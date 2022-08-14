async function initChart(chartname, xValues, yValues, barColors) {
    console.log("chartName ==== ", chartname)

    new Chart(chartname, {
        type: "bar",
        data: {
            labels: xValues,
            datasets: [{
                backgroundColor: '#CCDDEA',
                data: yValues
            }]
        },
        options: {
            scales : {
                y: {

                    beginAtZero: true
                },
                yAxes:
                    [{
                        ticks: {
                            suggestedMax: 5,
                            min: 0,
                            // fontSize : 14,
                        }
                    }]
            },
            legend: {display: false},
            title: {
                display: true,
                text: chartname
            }

        }
    });
}(
    "LeadTimeForChange",
        ["1week", "2week", "3week", "4week", "5week"],
        [55, 49, 44, 24, 30],
        ["red", "green","blue","orange","brown"]
);


async function chartChange(){

    const start_time = document.getElementById('start_time').value;
    const end_time = document.getElementById('end_time').value;
    const repo = document.getElementById('repo').value;

    if(!start_time || !end_time) return;



    let returnVal1 = await getDoraMetricsbyAxios(start_time, end_time, repo, 'http://localhost:8080/dorametric/lead-time-for-change');
    let leadTimeForChange = returnVal1[0];
    let date_arr = returnVal1[1];

    console.log("kkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n",
        leadTimeForChange[0].average_time_data, "\n==",
        date_arr);

    window.onload = initChart(
        "LeadTimeForChange",
        date_arr,
        leadTimeForChange[0].average_time_data,
        ["blue"],
        // "Elite",
    );

    let returnVal2 = await getDoraMetricsbyAxios(start_time, end_time, repo, 'http://localhost:8080/dorametric/deployment-frequency');
    let deploymentFrequency = returnVal2[0];

    window.onload = initChart(
        "DeploymentFrequency",
        date_arr,
        deploymentFrequency[0].average_time_data,
        ["red"],
    );

    window.onload = initChart(
        "ChangeFailureRate",
        ["1week", "2week", "3week", "4week", "5week"],
        [55, 49, 44, 24, 30],
        ["red", "green","blue","orange","brown"]
    );

    window.onload = initChart(
        "MTTR",
        ["1week", "2week", "3week", "4week", "5week"],
        [55, 49, 44, 24, 30],
        ["red", "green","blue","orange","brown"]
    );
}

async function getDoraMetricsbyAxios(start_time, end_time, repo, url, name) {
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

    testMessage =
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