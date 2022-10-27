const axios = require('axios');
async function blobExtract(fileUrl,context){
    const options = '{"responseType": "blob"}';
    let objList = [];
    const response = await axios.get(fileUrl,options);
    const avroFile = response.data;
    try{
        let arr = avroFile.split('{');
        for (const element of arr) {
            let a = element.replace(/['}]+/g, '');
            if(a.startsWith('"hook_event')){
                let nowObj = {};
                let splitByComma = a.split(",");
                for(const element of splitByComma) {
                    let nowLine = element;
                    if(nowLine.startsWith("\"")){
                        let lastLineSplit = nowLine.split("\"");
                        let indexDoublePoint = lastLineSplit.indexOf(":");
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

module.exports.blobExtract = blobExtract;