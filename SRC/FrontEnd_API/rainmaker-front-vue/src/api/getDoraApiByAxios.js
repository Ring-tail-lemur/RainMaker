const axios = require('axios');

async function getDoraMetricsbyAxios(start_time, end_time, repo, url) {
    const Message = await axios.get(url, {
        params : {
            start_time : start_time,
            end_time : end_time,
            repo : repo
        }
    })

    console.log("================================== Message\n", Message.data);


    let info;
    if (Message.data.leadTimeForChangeAverageMap) info = Message.data.leadTimeForChangeAverageMap;
    if (Message.data.deploymentFrequencyMap) info = Message.data.deploymentFrequencyMap;

    console.log("================================== Map\n", info);

    const sort = Object.entries(info).sort();
    console.log("*****************", sort[0][0], sort[0][1]);
    let average_time = [];
    let date_arr = [];
    for(var i =0; i< sort.length; i++){
        average_time.push(sort[i][1]);
        date_arr.push(sort[i][0]);
    }

    const testMessage =
        [
            {
                start_time : Message.data.start_time,
                end_time : Message.data.end_time,
                level : Message.data.level,
                average_time_data : average_time
            }
        ]

    console.log("111111111111111111111111 testMsg : " , testMessage, date_arr);
    return [testMessage, date_arr];
}


module.exports.getDoraMetricsbyAxios = getDoraMetricsbyAxios;