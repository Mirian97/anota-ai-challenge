import {
  GetObjectCommand,
  PutObjectCommand,
  S3Client,
} from "@aws-sdk/client-s3";

const client = new S3Client({ region: "us-east-2" });

export const handler = async (event) => {
  try {
    for (const record of event.Records) {
      console.log(
        "Iniciando processamento de mensagem",
        JSON.stringify(record)
      );

      try {
        const rawBody = JSON.parse(record.body);
        const body = JSON.parse(rawBody.Message);
        const ownerId = body.ownerId;
        const bucketName = "menu-catalog-marketplace";
        const filename = `${ownerId}-catalog.json`;
        console.log("Trying to get S3 object: ", {
          bucket: bucketName,
          filename,
        });
        const catalogContent = await getS3Object(bucketName, filename);
        console.log("Content obtained:", catalogContent);
        const catalogData = catalogContent
          ? JSON.parse(catalogContent)
          : { products: [], categories: [] };

        if (body.type == "product") {
          updateOrAddItem(catalogData.products, body);
        }
        if (body.type == "category") {
          updateOrAddItem(catalogData.categories, body);
        }
        if (body.type == "delete-product") {
          deleteS3Item(catalogData.products, body);
        }
        if (body.type == "delete-category") {
          deleteS3Item(catalogData.categories, body);
        }
        await putS3Object(bucketName, filename, JSON.stringify(catalogData));
      } catch (processError) {
        console.error("Error in individual proccess:", processError);
      }
    }
    return { status: "sucesso" };
  } catch (error) {
    console.error("Complete Error Log:", error);
    throw new Error("Erro ao processar mensagem do SQS");
  }
};

async function getS3Object(bucket, key) {
  const objectCommand = new GetObjectCommand({
    Bucket: bucket,
    Key: key,
  });
  try {
    const response = await client.send(objectCommand);
    if (!response.Body) {
      console.error("Answer from S3 does not contain body!");
      return null;
    }
    if (
      response.Body &&
      typeof response.Body.transformToString === "function"
    ) {
      return await response.Body.transformToString("utf-8");
    } else if (response.Body && typeof response.Body.pipe === "function") {
      return await streamToString(response.Body);
    } else {
      return await response.Body.toString("utf-8");
    }
  } catch (error) {
    if (error.name === "NoSuchKey") {
      console.log(`File ${key} not found in bucket ${bucket}`);
      return null;
    }
    console.error("Detailed error when fetching object:", error);
    throw error;
  }
}

function updateOrAddItem(catalog, newItem) {
  const index = catalog.findIndex((item) => item.id === newItem.id);
  if (index !== -1) {
    catalog[index] = { ...catalog[index], ...newItem };
  } else {
    catalog.push(newItem);
  }
}

async function putS3Object(dstBucket, dstKey, content) {
  try {
    const putCommand = new PutObjectCommand({
      Bucket: dstBucket,
      Key: dstKey,
      Body: content,
      ContentType: "application/json",
    });
    const putResult = await client.send(putCommand);
    return putResult;
  } catch (error) {
    console.log(error);
    return;
  }
}

async function streamToString(stream) {
  return new Promise((resolve, reject) => {
    const chunks = [];

    if (!(stream instanceof Readable)) {
      console.error("Not is a legible stream:", stream);
      reject(new Error("Inavlid stream"));
      return;
    }
    stream.on("data", (chunk) => {
      chunks.push(chunk);
    });
    stream.on("end", () => {
      const content = Buffer.concat(chunks).toString("utf-8");
      resolve(content);
    });
    stream.on("error", (error) => {
      console.error("Error when streamming:", error);
      reject(error);
    });
  });
}
