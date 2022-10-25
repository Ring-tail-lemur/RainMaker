const axios = require('axios');
async function blobModule(fileUrl,context){
    const options = '{"responseType": "blob"}';
    let objList = [];
    const response = await axios.get(fileUrl,options);
    const avroFile = response.data;
    try{
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
    }catch(e){
        context.log(e);
    }
    
    return objList;
}

module.exports.blobModule = blobModule;