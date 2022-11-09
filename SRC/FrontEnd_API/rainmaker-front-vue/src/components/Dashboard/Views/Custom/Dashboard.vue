<template>
  <div v-if="waiting">
    <div class="row">
      <div class="col-md-4">
        <h4 class="card-title">기간 선택</h4>
        <div class="form-group">
          <el-date-picker v-model="startTime" type="daterange" placeholder="Pick a day"
                          start-placeholder="시작일"
                          end-placeholder="종료일"
                          align="right"
                          range-separator="-"
                          :picker-options="{ disabledDate: (time) => disabledEndDate(time, null) }">
          </el-date-picker>
        </div>
      </div>
      <div class="col-md-2">
        <h4 class="card-title">단위</h4>
        <drop-down>
          <p-button slot="title"
                    class="dropdown-toggle"
                    data-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                    type="neutral"
                    style="border: 1px solid gainsboro"
                    block
                    round>
            {{ unit }}
          </p-button>
          <a class="dropdown-item" v-on:click="changeUnit('주')">주</a>
          <a class="dropdown-item" v-on:click="changeUnit('일')">일</a>
        </drop-down>
      </div>
      <div class="col-sm-3">
        <h4 class="card-title">리포지토리 선택</h4>
        <fg-input>
          <el-select multiple class="select-primary"
                     size="large"
                     v-model="selects.multiple"
                     placeholder="Multiple Select">
            <el-option v-for="option in selects.repositories"
                       class="select-primary"
                       :value="option.repositoryId"
                       :label="option.repositoryName"
                       :key="option.repositoryName">
            </el-option>
          </el-select>
        </fg-input>
      </div>
      <div class="col-md-2">
        <br><br><br>
        <p-button v-on:click="submitButtonPush()">제출하기</p-button>
      </div>
    </div>


    <div class="row">
      <div class="col-lg-6 col-sm-6" @mouseover="showBreakDown" @mouseleave="hideBreakDown">
        <chart-card :chart-data="LeadTimeForChange.data"
                    chart-id="activity-chart"
                    :color="LeadTimeForChange.color"
                    :stacked="stacked"
                    :suggestedMax="2000"
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
          <template slot="footer-title">
            <div class="stats">
              <i class="fa fa-check"></i> 더보기 버튼을 통해 자세한 정보를 제공받으세요!
            </div>
          </template>
          <p-button slot="footer-right" aria-label="add button" :type="typeChange(LeadTimeForChange.rate)" round icon
                    size="sm" onclick="location.href='/dashboard/lead-time-for-change'">
            <i class="nc-icon nc-simple-add"></i>
          </p-button>
        </chart-card>
      </div>

      <div class="col-lg-6 col-sm-6">
        <chart-card :chart-data="DeploymentFrequency.data"
                    chart-id="emails-chart"
                    :color="DeploymentFrequency.color"
                    chart-title="Deployment Frequency"
                    :suggestedMax="10"
                    :chart-options="DeploymentFrequency.drawBaseLine"
                    :class="'border-' + DeploymentFrequency.rate">
        <span slot="hover-slot" class="tooltip-custom">
          <i class="nc-icon nc-alert-circle-i"></i>
          <span class="tooltip-custom-text-left">
            <b>Deployment Frequency</b><br><br>
            일정 기간동안 배포한 횟수<br>
            <small><b>뛰어난 팀은 변경사항을 자주 배포하고 있습니다.</b></small>
          </span>
        </span>
          <span slot="title">배포 빈도</span>
          <badge slot="title-label" :type="DeploymentFrequency.rate">{{ DeploymentFrequency.rate }}</badge>
          <template slot="footer-title">
            <div class="stats">
              <i class="fa fa-check"></i> 더보기 버튼을 통해 자세한 정보를 제공받으세요!
            </div>
          </template>
          <p-button slot="footer-right" aria-label="add button" :type="typeChange(DeploymentFrequency.rate)" round icon
                    size="sm" onclick="location.href='/dashboard/deployment-frequency'">
            <i class="nc-icon nc-simple-add"></i>
          </p-button>
        </chart-card>
      </div>

      <div class="col-lg-6 col-sm-6">
        <chart-card :chart-data="ChangeFailureRate.data"
                    chart-id="active-countries-chart"
                    :color="ChangeFailureRate.color"
                    chart-title="Change Failure Rate"
                    :suggestedMax="1"
                    :chart-options="ChangeFailureRate.drawBaseLine"
                    :class="'border-' + ChangeFailureRate.rate">
        <span slot="hover-slot" class="tooltip-custom">
          <i class="nc-icon nc-alert-circle-i"></i>
          <span class="tooltip-custom-text-right">
            <b>Change Failure Rate</b><br><br>
            장애를 일으키는 배포의 비율<br>
          </span>
        </span>
          <span slot="title">변경 실패율</span>
          <badge slot="title-label" :type="ChangeFailureRate.rate">{{ ChangeFailureRate.rate }}</badge>
          <template slot="footer-title">
            <div class="stats">
              <i class="fa fa-check"></i> 더보기 버튼을 통해 자세한 정보를 제공받으세요!
            </div>
          </template>
          <p-button slot="footer-right" aria-label="add button" :type="typeChange(ChangeFailureRate.rate)" round icon
                    size="sm" onclick="location.href='/dashboard/change-failure-rate'">
            <i class="nc-icon nc-simple-add"></i>
          </p-button>
        </chart-card>
      </div>

      <div class="col-lg-6 col-sm-6">
        <chart-card :chart-data="MTTR.data"
                    chart-id="active-countries-chart"
                    :color="MTTR.color"
                    chart-title="Mean Time To Recover"
                    :suggestedMax="200"
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
          <template slot="footer-title">
            <div class="stats">
              <i class="fa fa-check"></i> 더보기 버튼을 통해 자세한 정보를 제공받으세요!
            </div>
          </template>
          <p-button slot="footer-right" aria-label="add button" :type="typeChange(MTTR.rate)"
                    round icon size="sm" onclick="location.href='/dashboard/mean-time-to-recover'">
            <i class="nc-icon nc-simple-add"></i>
          </p-button>
        </chart-card>
      </div>
    </div>


  </div>
  <loading-main-panel v-else="waiting"></loading-main-panel>
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
  component: import('./../Maps/WorldMap.vue'),
  loading: Loading,
  delay: 200
})
const DATA_COUNT = 7;
const NUMBER_CFG = {count: DATA_COUNT, min: -100, max: 100};

export default {
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
  /**
   * Chart data used to render stats, charts. Should be replaced with server data
   */
  watch: {
    endTime() {
      if (this.startTime != '') {
        if (this.endTime < this.startTime) {
          alert("끝시간은 시작시간보다 커야합니다");
          this.endTime = '';
        }
      }
    },
    startTime() {
      if (this.endTime != '') {
        if (this.endTime < this.startTime) {
          alert("시작시간은 끝시간보다 작아야합니다");
          this.startTime = '';
        }
      }
    },
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
      DeploymentFrequency: {
        color: "#41B883",
        rate: "fruit",
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
            y: 7,
            style: 'rgba(104,179,200,0.2)',
            text: 'flower'
          }, {
            y: 1,
            style: 'rgba(252,196,104,0.2)',
            text: 'sprout'
          }, {
            y: 0.25,
            style: 'rgba(239,129,86,0.2)',
            text: '  seed'
          }]
        },
      },
      ChangeFailureRate: {
        color: "#68B3C8",
        rate: "flower",
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
            y: 0.15,
            style: 'rgba(65,184,131,0.2)',
            text: 'fruit'
          }, {
            y: 0.46,
            style: 'rgba(252,196,104,0.2)',
            text: 'sprout'
          }]
        },
      },
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
          {value: '510731046', label: 'RainMaker'},
          {value: '517528822', label: 'test-for-fake-project'},
          {value: '193281821', label: 'gugudan'},
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
      Today.setMonth(Today.getMonth() - 1);
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
        alert("기간을 선택해주세요!");
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
      if (start_time === undefined || end_time === undefined) {
        alert("기간을 선택해주세요");
        return;
      }
      if (repo_id === undefined || repo_id.length === 0) {
        alert("Github 리포지토리를 선택해주세요!");
        return;
      }
      this.getDoraMetric(start_time, end_time, repo_id, "lead-time-for-change");
      this.getDoraMetric(start_time, end_time, repo_id, "time-to-restore-service");
      this.getDoraMetric(start_time, end_time, repo_id, "change-failure-rate");
      this.getDoraMetric(start_time, end_time, repo_id, "deployment-frequency");
    },
    getMessageBody(Message) {
      let BodyData = Message.data;
      if (BodyData.hasOwnProperty('leadTimeForChangeDetailMap')) {
        return BodyData.leadTimeForChangeDetailMap;
      } else if (BodyData.hasOwnProperty('changeFailureRateMap')) {
        return BodyData.changeFailureRateMap;
      } else if (BodyData.hasOwnProperty('deploymentFrequencyMap')) {
        return BodyData.deploymentFrequencyMap;
      } else if (BodyData.hasOwnProperty('timeToRestoreServiceMap')) {
        return BodyData.timeToRestoreServiceMap;
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
        case "time-to-restore-service":
          this.setTimeToRestoreService(start_date, end_date, info, level);
          break;
        case "change-failure-rate":
          this.setChangeFailureRate(start_date, end_date, info, level);
          break;
        case "deployment-frequency":
          this.setDeploymentFrequency(start_date, end_date, info, level);
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
        return "#ef8156";
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
      this.LeadTimeForChangeDetailDataSets = this.LeadTimeForChangeDetailDataSetsWeeks;

      let firstAndFinalDay = this.extractFirstAndFinalDay(date_labels);
      let calculateWithoutZero = this.slice7DaysCalculateWithoutZero(data_series);
      this.LeadTimeForChange.day_data.labels = date_labels;
      this.LeadTimeForChange.day_data.series = data_series;
      this.LeadTimeForChange.week_data.labels = firstAndFinalDay;
      this.LeadTimeForChange.week_data.series = calculateWithoutZero;
      this.LeadTimeForChange.data.labels = firstAndFinalDay;
      this.LeadTimeForChange.data.series = calculateWithoutZero;
      this.LeadTimeForChange.rate = level;
      this.LeadTimeForChange.color = this.colorPickByLevel(level);
    },
    setDeploymentFrequency(start_date, end_date, info, level) {
      let date_labels = [];
      let data_series = [];

      let diffDate = (end_date.getTime() - start_date.getTime()) / (1000 * 60 * 60 * 24);
      let week = diffDate / 7;
      let other = diffDate - week;


      while (start_date <= end_date) {
        date_labels.push(this.dateFormat(start_date));
        data_series.push(info[this.dateFormat(start_date)] || 0);
        start_date.setDate(start_date.getDate() + 1);
      }


      let slice7Days = this.slice7Days(data_series);
      let firstAndFinalDay = this.extractFirstAndFinalDay(date_labels);
      console.log("slice :", data_series);
      console.log("firstAndFinalDay :", date_labels);
      console.log(this.DeploymentFrequency.day_data);
      this.DeploymentFrequency.day_data.labels = date_labels;
      this.DeploymentFrequency.day_data.series = data_series;
      console.log(this.DeploymentFrequency.day_data);
      this.DeploymentFrequency.week_data.labels = firstAndFinalDay;
      this.DeploymentFrequency.week_data.series = slice7Days;
      this.DeploymentFrequency.data.labels = firstAndFinalDay;
      this.DeploymentFrequency.data.series = slice7Days;
      this.DeploymentFrequency.rate = level;
      this.DeploymentFrequency.color = this.colorPickByLevel(level);
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
      this.MTTR.data.labels = firstAndFinalDay;
      this.MTTR.data.series = calculateWithoutZero;
      this.MTTR.rate = level;
      this.MTTR.color = this.colorPickByLevel(level);
    },
    setChangeFailureRate(start_date, end_date, info, level) {
      let date_labels = [];
      let data_series = [];

      while (start_date <= end_date) {
        date_labels.push(this.dateFormat(start_date));
        data_series.push(info[this.dateFormat(start_date)] || 0);
        start_date.setDate(start_date.getDate() + 1);
      }
      let firstAndFinalDay = this.extractFirstAndFinalDay(date_labels);
      let calculateWithoutZero = this.slice7DaysCalculateWithoutZero(data_series);
      console.log("calculateWithoutZero : ", calculateWithoutZero);
      this.ChangeFailureRate.day_data.labels = date_labels;
      this.ChangeFailureRate.day_data.series = data_series;
      this.ChangeFailureRate.week_data.labels = firstAndFinalDay;
      this.ChangeFailureRate.week_data.series = calculateWithoutZero;
      this.ChangeFailureRate.data.labels = firstAndFinalDay;
      this.ChangeFailureRate.data.series = calculateWithoutZero;
      this.ChangeFailureRate.rate = level;
      this.ChangeFailureRate.color = this.colorPickByLevel(level);
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
    changeUnit(unit) {
      this.unit = unit;
      if(unit === '주') {
        this.LeadTimeForChangeDetailDataSets = JSON.parse(JSON.stringify(this.LeadTimeForChangeDetailDataSetsWeeks));
        this.LeadTimeForChange.data = JSON.parse(JSON.stringify(this.LeadTimeForChange.week_data));
        this.DeploymentFrequency.data = JSON.parse(JSON.stringify(this.DeploymentFrequency.week_data));
        this.ChangeFailureRate.data = JSON.parse(JSON.stringify(this.ChangeFailureRate.week_data));
        this.MTTR.data = JSON.parse(JSON.stringify(this.MTTR.week_data));
      }
      if(unit === '일') {
        this.LeadTimeForChangeDetailDataSets = JSON.parse(JSON.stringify(this.LeadTimeForChangeDetailDataSetsDaily));
        this.LeadTimeForChange.data = JSON.parse(JSON.stringify(this.LeadTimeForChange.day_data));
        this.DeploymentFrequency.data = JSON.parse(JSON.stringify(this.DeploymentFrequency.day_data));
        this.ChangeFailureRate.data = JSON.parse(JSON.stringify(this.ChangeFailureRate.day_data));
        this.MTTR.data = JSON.parse(JSON.stringify(this.MTTR.day_data));
      }
    }
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
