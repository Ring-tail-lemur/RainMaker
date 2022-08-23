const Chart = require('chart.js/auto').default;
const getDoraApi = require('../api/getDoraApiByAxios');
// const url ='http://localhost:8080';
const url = 'https://spring-api-server.azurewebsites.net';

async function initChart(chartname, xValues, yValues) {
    console.log("chartName ==== ", chartname)

    const chart = await new Chart(chartname, {
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

    return chart;
}


async function chartChange(start_time, end_time, repo){

    if(!start_time || !end_time) return;

    let returnVal1 = await getDoraApi.getDoraMetricsbyAxios(start_time, end_time, repo, url + '/dorametric/lead-time-for-change');
    let leadTimeForChange = returnVal1[0];
    let date_arr = returnVal1[1];

    console.log("kkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n",
        leadTimeForChange[0].average_time_data, "\n==",
        date_arr);

    const initChart1 = initChart(
        "LeadTimeForChange",
        date_arr,
        leadTimeForChange[0].average_time_data,
        ["blue"],
        // "Elite",
    );

    let returnVal2 = await getDoraApi.getDoraMetricsbyAxios(start_time, end_time, repo, url + '/dorametric/deployment-frequency');
    let deploymentFrequency = returnVal2[0];

    const initChart2 = initChart(
        "DeploymentFrequency",
        date_arr,
        deploymentFrequency[0].average_time_data,
        ["red"],
    );

    let returnVal3 = await getDoraApi.getDoraMetricsbyAxios(start_time, end_time, repo, url + '/dorametric/change-failure-rate');
    let changeFailureRate = returnVal3[0];

    const initChart3 = initChart(
        "ChangeFailureRate",
        date_arr,
        changeFailureRate[0].average_time_data,
    );

    let returnVal4 = await getDoraApi.getDoraMetricsbyAxios(start_time, end_time, repo, url + '/dorametric/time-to-restore-service');
    let MTTR = returnVal4[0];

    const initChart4 = initChart(
        "MTTR",
        date_arr,
        MTTR[0].average_time_data,
    );

    console.log("*******************************\n", await initChart1, await initChart2, await initChart3, await initChart4);
    return [initChart1, initChart2, initChart3, initChart4];
}

module.exports.initChart = initChart;
module.exports.chartChange = chartChange;
