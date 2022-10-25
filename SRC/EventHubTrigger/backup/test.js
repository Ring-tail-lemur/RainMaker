const axios = require('axios');
const avro = require('avro-js');
async function test(){
    const fileUrl = "https://githubparserfromcosmosdb.blob.core.windows.net/event-hub-storage/httptriggereventhubs/githubhttpeventhub/1/2022/10/24/05/48/12.avro";
    const options = '{"responseType": "blob"}';
    let objList = [];
    const response = await axios.get(fileUrl,options);

    const avroFile = response.data;
    let arr = avroFile.split('{');
    for (let i = 0; i < arr.length; i++) {
        let a = arr[i].replace(/['}]+/g, '');
        if(a.startsWith('"hook_event')){
            let nowObj = {};
            let splitByComma = a.split(",");
            for(let i = 0; i < splitByComma.length; i++) {
                nowLine = splitByComma[i];
                if(nowLine.startsWith("\"")){
                    lastLineSplit = nowLine.split("\"");
                    indexDoublePoint = lastLineSplit.indexOf(":");
                    nowObj[lastLineSplit[indexDoublePoint-1]] = lastLineSplit[indexDoublePoint+1]; 
                }
            }
            objList.push(nowObj);
        }
    }
    console.log(objList);
}

test();