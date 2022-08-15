// const Chart = require('chart.js/auto');
const Chart = require('chart.js/auto').default;
// const getDoraApi = require('../api/getDoraApiByAxios');
// import Chart from 'chart.js';

async function initChart(chartname, xValues, yValues) {
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
}


// async function chartChange(start_time, end_time, repo){
//
//     if(!start_time || !end_time) return;
//
//     let returnVal1 = await getDoraApi.getDoraMetricsbyAxios(start_time, end_time, repo, 'http://localhost:8080/dorametric/lead-time-for-change');
//     let leadTimeForChange = returnVal1[0];
//     let date_arr = returnVal1[1];
//
//     console.log("kkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n",
//         leadTimeForChange[0].average_time_data, "\n==",
//         date_arr);
//
//     window.onload = initChart(
//         "LeadTimeForChange",
//         date_arr,
//         leadTimeForChange[0].average_time_data,
//         ["blue"],
//         // "Elite",
//     );
//
//     let returnVal2 = await getDoraApi.getDoraMetricsbyAxios(start_time, end_time, repo, 'http://localhost:8080/dorametric/deployment-frequency');
//     let deploymentFrequency = returnVal2[0];
//
//     window.onload = initChart(
//         "DeploymentFrequency",
//         date_arr,
//         deploymentFrequency[0].average_time_data,
//         ["red"],
//     );
//
//     window.onload = initChart(
//         "ChangeFailureRate",
//         ["1week", "2week", "3week", "4week", "5week"],
//         [55, 49, 44, 24, 30],
//         ["red", "green","blue","orange","brown"]
//     );
//
//     window.onload = initChart(
//         "MTTR",
//         ["1week", "2week", "3week", "4week", "5week"],
//         [55, 49, 44, 24, 30],
//         ["red", "green","blue","orange","brown"]
//     );
// }

module.exports.initChart = initChart;