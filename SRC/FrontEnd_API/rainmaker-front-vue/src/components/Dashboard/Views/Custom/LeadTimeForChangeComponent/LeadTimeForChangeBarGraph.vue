<template>
  <div @mouseover="showBreakDown" @mouseleave="hideBreakDown">
    <chart-card :chart-data="LeadTimeForChange.data"
                chart-id="activity-chart"
                :color="LeadTimeForChange.color"
                :stacked="stacked"
                :suggestedMax="50000"
                chart-title="Lead Time For Change"
                :chart-options="LeadTimeForChange.drawBaseLine"
                :class="'border-' + LeadTimeForChange.rate">
      <span slot="hover-slot" class="tooltip-custom">
        <i class="nc-icon nc-alert-circle-i"></i>
        <span class="tooltip-custom-text-right">
          <b>Lead Time For Change</b><br><br>
          커밋이 배포되기까지 평균적으로 걸리는 시간<br>
          <b>변경 리드 타임</b> = <i>Commit Time</i> + <i>Pickup Time</i> + <i>Review Time</i> + <i>Deploy Time</i><br><br>
          <b>Commit Time</b> : 첫 커밋부터 PullRequest 오픈까지 걸린 시간<br>
          <b>Pickup Time</b>: PullRequest가 열린시간부터 첫 리뷰가 달리기까지 컬린 시간<br>
          <b>Review Time</b>: 첫 리뷰가 달린 시간부터 PullRequest가 병합될 때까지 걸린 시간<br>
          <b>Deploy Time</b>: PullReqeust가 병합된 시간부터 배포되기까지 걸린 시간<br>
          <small><b>변경 리드 타임은 배포된 날자를 기준으로 계산합니다.</b></small>
        </span>
      </span>
      <span slot="title">변경 리드 타임</span>
      <badge slot="title-label" :type="LeadTimeForChange.rate">{{ LeadTimeForChange.rate }}</badge>
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
  name : "leadTimeForChangeBar",
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
      stacked: false,
      LeadTimeForChangeDetailDataSets: [],
      LeadTimeForChangeDetailDataSetsDaily: [],
      LeadTimeForChangeDetailDataSetsWeeks: [],
      LeadTimeForChange: {
        color: "#ef8156",
        rate: "seed",
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
            y: 1440,
            style: 'rgba(65,184,131,0.2)',
            text: 'fruit'
          }, {
            y: 10080,
            style: 'rgba(104,179,200,0.2)',
            text: 'flower'
          }, {
            y: 43200,
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
    hideBreakDown() {
      this.stacked = false;
      delete this.LeadTimeForChange.data.datasets
    },
    showBreakDown() {
      this.stacked = true;
      this.LeadTimeForChange.data.datasets = this.LeadTimeForChangeDetailDataSets
    },
    getStackedColor(detailName) {
      switch (detailName) {
        case "codingTime":
          return "#874C62"
        case "pickupTime":
          return "#C98474"
        case "reviewTime":
          return "#F2D388"
        case "deployTime":
          return "#A7D2CB"
      }
    },
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
    async createdMethod() {
      let Today = new Date();
      const FormatToday = this.dateFormat(Today);
      Today.setDate(Today.getDate()- 6);
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
    submitButtonPush() {
      this.unit = '주';
      try {
        this.getAllDoraMetric(this.dateFormat(this.startTime[0]), this.dateFormat(this.startTime[1]), this.selects.multiple);
      } catch (e) {
        alert("잘못 입력하셨습니다.");
        console.error(e);
        return;
      }
    },
    disabledEndDate(date, departureDate) {
      // If departureDate then return valid dates after departureDate

      if (departureDate) {
        return date.getTime() < departureDate
      } else {
        // If !departureDate then return valid dates after today
        return date.getTime() > Date.now() || date.getTime() < Date.now() - 7776000000;
      }
    },
    getAllDoraMetric(start_time, end_time, repo_id) {
      if (start_time === undefined || end_time === undefined || repo_id === undefined) {
        alert("잘못 입력하셨습니다.");
        return;
      }
      this.getDoraMetric(start_time, end_time, repo_id, "lead-time-for-change");

    },
    getMessageBody(Message) {
      let BodyData = Message.data;
      if (BodyData.hasOwnProperty('leadTimeForChangeDetailMap')) {
        return BodyData.leadTimeForChangeDetailMap;
      }
    },
    async getDoraMetric(start_time, end_time, repo_id, MetricName) {
      console.log(MetricName + "이 들어왔네")
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

      switch (MetricName) {
        case "lead-time-for-change":
          this.setLeadTimeForChange(start_date, end_date, info, level);
          break;
      }
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
        return "#ef8156";E
      } else if (level == "sprout") {
        return "#fcc468";
      } else if (level == "flower") {
        return "#68B3C8";
      } else if (level == "fruit") {
        return "#41B883";
      }
    },
    setLeadTimeForChange(start_date, end_date, info, level) {
      let date_labels = [];
      let data_series = [];
      const coding_time = [];
      const pickup_time = [];
      const review_time = [];
      const deploy_time = [];

      while (start_date <= end_date) {
        let detail = info[this.dateFormat(start_date)] || {};
        coding_time.push(detail.codingTime || 0);
        pickup_time.push(detail.pickupTime || 0);
        review_time.push(detail.reviewTime || 0);
        deploy_time.push(detail.deployTime || 0);
        date_labels.push(this.dateFormat(start_date));
        data_series.push(detail.totalValue);
        start_date.setDate(start_date.getDate() + 1);
      }

      this.LeadTimeForChangeDetailDataSetsWeeks = [
        {
          label: 'coding time',
          data: this.slice7DaysCalculateWithoutZero(coding_time),
          backgroundColor: this.getStackedColor("codingTime"),
        },
        {
          label: 'pickup time',
          data: this.slice7DaysCalculateWithoutZero(pickup_time),
          backgroundColor: this.getStackedColor("pickupTime"),
        },
        {
          label: 'review time',
          data: this.slice7DaysCalculateWithoutZero(review_time),
          backgroundColor: this.getStackedColor("reviewTime"),
        },
        {
          label: 'deploy time',
          data: this.slice7DaysCalculateWithoutZero(deploy_time),
          backgroundColor: this.getStackedColor("deployTime"),
        },
      ]
      this.LeadTimeForChangeDetailDataSetsDaily = [
        {
          label: 'coding time',
          data: coding_time,
          backgroundColor: this.getStackedColor("codingTime"),
        },
        {
          label: 'pickup time',
          data: pickup_time,
          backgroundColor: this.getStackedColor("pickupTime"),
        },
        {
          label: 'review time',
          data: review_time,
          backgroundColor: this.getStackedColor("reviewTime"),
        },
        {
          label: 'deploy time',
          data: deploy_time,
          backgroundColor: this.getStackedColor("deployTime"),
        },
      ]
      this.LeadTimeForChangeDetailDataSets = this.LeadTimeForChangeDetailDataSetsDaily;

      let firstAndFinalDay = this.extractFirstAndFinalDay(date_labels);
      let calculateWithoutZero = this.slice7DaysCalculateWithoutZero(data_series);
      this.LeadTimeForChange.day_data.labels = date_labels;
      this.LeadTimeForChange.day_data.series = data_series;
      this.LeadTimeForChange.week_data.labels = firstAndFinalDay;
      this.LeadTimeForChange.week_data.series = calculateWithoutZero;
      this.LeadTimeForChange.data.labels = date_labels;
      this.LeadTimeForChange.data.series = data_series;
      this.LeadTimeForChange.rate = level;
      this.LeadTimeForChange.color = this.colorPickByLevel(level);
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
    await this.createdMethod();
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
