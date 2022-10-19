import {Bar} from 'vue-chartjs'
import {hexToRGB} from "./utils";
import reactiveChartMixin from "./mixins/reactiveChart";


export default {
  name: 'bar-chart',
  extends: Bar,
  mixins: [reactiveChartMixin],
  data() {
    return {
      horizontalLinePlugin : {
        afterDraw: function (chartInstance) {
          let yScale = chartInstance.scales["y-axis-0"];
          let canvas = chartInstance.chart;
          let ctx = canvas.ctx;
          let index;
          let line;
          let style;

          if (chartInstance.options.horizontalLine) {
            for (index = 0; index < chartInstance.options.horizontalLine.length; index++) {
              line = chartInstance.options.horizontalLine[index];
              style = line.style;

              let yValue;
              if (line.y) {
                yValue = yScale.getPixelForValue(line.y);
              } else {
                yValue = 0;
              }

              ctx.lineWidth = 1;
              if (yValue) {
                ctx.beginPath();
                ctx.moveTo(50, yValue);
                ctx.lineTo(canvas.width, yValue);
                ctx.strokeStyle = style;
                ctx.stroke();
              }

              if (line.text) {
                ctx.fillStyle = style;
                ctx.setLineDash([3]);
                ctx.font = "15px sans-serif";
                // ctx.fillText(line.text, canvas.width - 50, yValue - ctx.lineWidth - 3);
              }
            }
          }
        }
      },
      defaultOptions: {
        tooltips: {
          tooltipFillColor: "rgba(0,0,0,0.5)",
          tooltipFontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
          tooltipFontSize: 14,
          tooltipFontStyle: "normal",
          tooltipFontColor: "#fff",
          tooltipTitleFontFamily: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
          tooltipTitleFontSize: 14,
          tooltipTitleFontStyle: "bold",
          tooltipTitleFontColor: "#fff",
          tooltipYPadding: 6,
          tooltipXPadding: 6,
          tooltipCaretSize: 8,
          tooltipCornerRadius: 6,
          tooltipXOffset: 10,
        },
        legend: {
          display: false
        },
        scales: {
          yAxes: [{
            stacked: false,
            ticks: {
              fontColor: "#9f9f9f",
              fontStyle: "bold",
              beginAtZero: true,
              maxTicksLimit: 5,
              padding: 20,
              suggestedMax: this.suggestedMax,
            },
            gridLines: {
              zeroLineColor: "transparent",
              display: true,
              drawBorder: false,
              color: '#9f9f9f',
            }
          }],
          xAxes: [{
            stacked: false,
            barPercentage: 0.4,
            gridLines: {
              zeroLineColor: "white",
              display: false,

              drawBorder: false,
              color: 'transparent',
            },
            ticks: {
              padding: 20,
              fontColor: "#9f9f9f",
              fontStyle: "bold"
            }
          }]
        }
      }
    }
  },
  watch: {
    stacked() {
      this.defaultOptions.scales.yAxes[0].stacked = this.stacked;
      this.defaultOptions.scales.xAxes[0].stacked = this.stacked;
      this.renderChart(this.chartData, this.options);
    }
  },
  props: {
    suggestedMax: { type: Number },
    stacked: {type: Boolean},
    labels: {
      type: [Object, Array],
      description: 'Chart labels. This is overridden when `data` is provided'
    },
    datasets: {
      type: [Object, Array],
      description: 'Chart datasets. This is overridden when `data` is provided'
    },
    data: {
      type: [Object, Array],
      description: 'Chart.js chart data (overrides all default data)'
    },
    color: {
      type: String,
      description: 'Chart color. This is overridden when `data` is provided'
    },
    extraOptions: {
      type: Object,
      description: 'Chart.js options'
    },
    title: {
      type: String,
      description: 'Chart title'
    },
  },
  methods: {
    assignChartData() {
      let {gradientFill} = this.assignChartOptions(this.defaultOptions);
      let color = this.color || this.fallBackColor;
      return {
        labels: this.labels || [],
        datasets: this.datasets ? this.datasets : [{
          label: this.title || '',
          backgroundColor: gradientFill,
          borderColor: color,
          pointBorderColor: "#FFF",
          pointBackgroundColor: color,
          pointBorderWidth: 2,
          pointHoverRadius: 4,
          pointHoverBorderWidth: 1,
          pointRadius: 4,
          fill: true,
          borderWidth: 1,
          data: this.data || []
        }]
      }
    },
    assignChartOptions(initialConfig) {
      let color = this.color || this.fallBackColor;
      const ctx = document.getElementById(this.chartId).getContext('2d');
      const gradientFill = ctx.createLinearGradient(0, 500, 0, 0);
      gradientFill.addColorStop(1, hexToRGB(color, 0.6));
      let extraOptions = this.extraOptions || {}
      return {
        ...initialConfig,
        ...extraOptions,
        gradientFill
      };
    },

  },
  created() {
    Chart.pluginService.register(this.horizontalLinePlugin);
  },
  mounted() {
    this.chartData = this.assignChartData({});
    this.options = this.assignChartOptions(this.defaultOptions);
    this.renderChart(this.chartData, this.options);
  }
}
