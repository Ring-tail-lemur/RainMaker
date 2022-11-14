<template>
  <div class="col-lg-6 col-sm-6">
    <chart-card :chart-data="MTTR.data"
                chart-id="active-countries-chart"
                :color="MTTR.color"
                chart-title="Mean Time To Recover"
                :suggestedMax="10"
                :chart-options="MTTR.drawBaseLine"
                :class="'border-' + MTTR.rate">
          <span slot="hover-slot" class="tooltip-custom">
            <i class="nc-icon nc-alert-circle-i"></i>
            <span class="tooltip-custom-text-left">
              <b>MTTR(Mean Time To Repair)</b><br><br>
              조직이 장애로부터 서비스를 복구하는 데 걸리는 시간<br>
          </span>
          </span>
      <span slot="title">평균 회복 시간</span>
      <badge slot="title-label" :type="MTTR.rate">{{ MTTR.rate }}</badge>
      <span slot="CustomSlot"><br></span>
      <div slot="footer">
        <hr>
        <div class="stats">
          <i class="fa fa-check"></i> 14일 동안의 지표입니다.
        </div>
      </div>
    </chart-card>
  </div>
</template>
<script>
import CircleChartCard from 'src/components/UIComponents/Cards/CircleChartCard.vue'
import ChartCard from 'src/components/UIComponents/Cards/ChartCard';
import StatsCard from 'src/components/UIComponents/Cards/StatsCard';
import {Badge} from 'src/components/UIComponents'
import Loading from 'src/components/Dashboard/Layout/LoadingMainPanel.vue'
import TaskList from "src/components/Dashboard/Views/Dashboard/Widgets/TaskList";
import {Button, DatePicker, Input, Option, Select, Slider, Tag, TimeSelect} from "element-ui";
import axios from "axios";
import setHeaderJWT from "@/util/setHeaderJWT";
import LoadingMainPanel from "@/components/Dashboard/Layout/LoadingMainPanel";

const WorldMap = () => ({
  component: import('@/components/Dashboard/Views/Maps/WorldMap.vue'),
  loading: Loading,
  delay: 200
})

export default {
  name : "meanTimeToRepairBar",
  components: {
    LoadingMainPanel,
    [DatePicker.name]: DatePicker,
    [TimeSelect.name]: TimeSelect,
    [Slider.name]: Slider,
    [Tag.name]: Tag,
    [Input.name]: Input,
    [Button.name]: Button,
    [Option.name]: Option,
    [Select.name]: Select,
    StatsCard,
    ChartCard,
    CircleChartCard,
    WorldMap,
    Badge,
    TaskList
  },
  watch: {
    unit() {

    }
  },
  data() {
    return {
      unit: "주",
      MTTR: {
        color: "#fcc468",
        rate: "sprout",
        data: {
          labels: [],
          series: []
        },
        day_data: {
          labels: [],
          series: []
        },
        week_data: {
          labels: [],
          series: []
        },
        drawBaseLine: {
          horizontalLine: [{
            y: 60,
            style: 'rgba(65,184,131,0.2)',
            text: 'fruit'
          }, {
            y: 1440,
            style: 'rgba(104,179,200,0.2)',
            text: 'flower'
          }, {
            y: 10080,
            style: 'rgba(252,196,104,0.2)',
            text: 'sprout'
          }]
        },
      },
      startTime: '',
      endTime: '',
      selects: {
        simple: '',
        repositories: [
        ],
        multiple: 'ARS'
      },
      waiting: false,
      interval: ''
    }
  },
  methods: {
    typeChange(rate) {
      switch (rate) {
        case 'seed':
          return 'danger';
        case 'sprout':
          return 'warning';
        case 'flower':
          return 'info'
        case 'fruit':
          return 'success';
      }
    },
    async createdMethod(showDate) {
      let Today = new Date();
      const FormatToday = this.dateFormat(Today);
      Today.setDate(Today.getDate()- showDate);
      const FormatLastMonth = this.dateFormat(Today);

      const repositories = await this.getRepositoryInfo();
      this.selects.repositories = repositories;

      const repositoryArr = [];

      repositories.forEach((repository) => {
        repositoryArr.push(repository['repositoryId']);
      });

      this.getAllDoraMetric(FormatLastMonth, FormatToday, repositoryArr);
    },
    async checkWaitingStatus() {
      let response;
      try {
        response = await axios.get(this.custom.defaultURL + "/api/user/check", {headers: setHeaderJWT()});
      } catch (e) {
        if (e.request.status === 445) {
          this.$router.push('/fakePage/CallAdminPlease');
        }
      }

      console.log(response.request.status);
      if (response.request.status === 200) {
        console.log(response.request.status)
        this.waiting = true;
        clearInterval(this.interval);
        await this.createdMethod()
      }
    },
    async getRepositoryInfo() {
      let axiosResponse;

      try {
        axiosResponse = await axios.get(this.custom.defaultURL + "/api/user/repositories", {
          headers: setHeaderJWT()
        });
        this.waiting = true
      } catch (e) {
        if (e.request.status === 444) {
          this.interval = setInterval(() => this.checkWaitingStatus(), 5000);
        }
      }
      return axiosResponse.data;
    },
    getAllDoraMetric(start_time, end_time, repo_id) {
      if (start_time === undefined || end_time === undefined || repo_id === undefined) {
        alert("잘못 입력하셨습니다.");
        return;
      }
      this.getDoraMetric(start_time, end_time, repo_id, "lead-time-for-change");
      this.getDoraMetric(start_time, end_time, repo_id, "time-to-restore-service");
      this.getDoraMetric(start_time, end_time, repo_id, "change-failure-rate");
      this.getDoraMetric(start_time, end_time, repo_id, "deployment-frequency");
    },
    getMessageBody(Message) {
      return Message.data.timeToRestoreServiceMap;
    },
    async getDoraMetric(start_time, end_time, repo_id, MetricName) {
      const Message = await axios.get(this.custom.defaultURL + "/api/dorametric/" + MetricName, {
        headers: setHeaderJWT(),
        params: {
          start_time: start_time,
          end_time: end_time,
          repo_id: repo_id.toString()
        }
      })

      const level = Message.data.level.toLowerCase();
      let info = this.getMessageBody(Message);

      const start_date = new Date(start_time);
      const end_date = new Date(end_time);

      this.setTimeToRestoreService(start_date, end_date, info, level);
    },
    dateFormat(date) {
      let month = date.getMonth() + 1;
      let day = date.getDate();

      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;

      return date.getFullYear() + '-' + month + '-' + day;
    },
    colorPickByLevel(level) {
      if (level == "seed") {
        return "#ef8156";
      } else if (level == "sprout") {
        return "#fcc468";
      } else if (level == "flower") {
        return "#68B3C8";
      } else if (level == "fruit") {
        return "#41B883";
      }
    },
    setTimeToRestoreService(start_date, end_date, info, level) {
      let date_labels = [];
      let data_series = [];

      while (start_date <= end_date) {
        date_labels.push(this.dateFormat(start_date));
        data_series.push(info[this.dateFormat(start_date)] || 0);
        start_date.setDate(start_date.getDate() + 1);
      }
      let firstAndFinalDay = this.extractFirstAndFinalDay(date_labels);
      let calculateWithoutZero = this.slice7DaysCalculateWithoutZero(data_series);
      this.MTTR.day_data.labels = date_labels;
      this.MTTR.day_data.series = data_series;
      this.MTTR.week_data.labels = firstAndFinalDay;
      this.MTTR.week_data.series = calculateWithoutZero;
      this.MTTR.data.labels = date_labels;
      this.MTTR.data.series = data_series;
      this.MTTR.rate = level;
      this.MTTR.color = this.colorPickByLevel(level);
    },
    slice7Days(arr) {
      arr = arr.map(element => {
        return element || 0
      });
      let ret = [];
      let week = parseInt(arr.length / 7);
      let day = arr.length % 7;
      let sum = 0;
      if (day !== 0) {
        arr.slice(0, day).forEach(element => sum += element);
        ret.push(sum);
      }
      for (let i = 0; i < week; i++) {
        sum = 0;
        arr.slice(7 * i + day, 7 * (i + 1) + day).forEach(element => sum += element);
        ret.push(sum);
      }
      return ret;
    },
    slice7DaysCalculateWithoutZero(arr) {
      arr = arr.map(element => {
        return element || 0
      });
      let ret = [];
      let week = parseInt(arr.length / 7);
      let day = arr.length % 7;
      let sum = 0;
      let size = day;
      if (day !== 0) {
        arr.slice(0, day).forEach(element => {
          if(element === 0) size--;
          sum += element
        });
        size===0 ? ret.push(0) : ret.push(sum / size);
      }
      for (let i = 0; i < week; i++) {
        sum = 0;
        size = 7;
        arr.slice(7 * i + day, 7 * (i + 1) + day).forEach(element => {
          if(element === 0) size--;
          sum += element
        });
        size===0 ? ret.push(0) : ret.push(sum / size);
      }
      return ret;
    },
    extractFirstAndFinalDay(arr) {
      let ret = [];
      let week = parseInt(arr.length / 7);
      let day = arr.length % 7;
      if (day !== 0) {
        ret.push([arr[0], ' ~ ',arr[day - 1]]);
      }
      for (let i = 0; i < week; i++) {
        ret.push([arr[7 * i + day], ' ~ ', arr[(7 * (i + 1)) + day - 1]]);
      }
      return ret;
    },
  },
  async created() {
    await this.createdMethod(14);
  }
}

</script>
<style>
.tooltip-custom {
  display: inline-block;
}

.tooltip-custom-text-right {
  display: none;
  position: absolute;
  width: max-content;
  margin-top: 20px;
  border: 2px solid white;
  border-radius: 10px;
  padding: 5px 15px;
  font-size: 1em;
  color: black;
  background: #f4f3ef !important;
  z-index: 1;
}


.tooltip-custom-text-left {
  display: none;
  position: absolute;
  width: max-content;
  margin-top: 20px;
  transform: translateX(-100%);
  border: 2px solid white;
  border-radius: 10px;
  padding: 5px 15px;
  font-size: 1em;
  color: black;
  background: #f4f3ef !important;
  z-index: 1;
}
.tooltip-custom:hover .tooltip-custom-text-right {
  display: inline;
}
.tooltip-custom:hover .tooltip-custom-text-left {
  display: inline;
}
</style>
