<template>
  <div v-if="waiting">
    <div class="row">
      <div class="col-md-3">
        <h4 class="card-title">시작 시간</h4>
        <div class="form-group">
          <el-date-picker v-model="startTime" type="date" placeholder="Pick a day"
                          :picker-options="{ disabledDate: (time) => disabledEndDate(time, null) }">
          </el-date-picker>
        </div>
      </div>
      <div class="col-md-3">
        <h4 class="card-title">끝 시간</h4>
        <div class="form-group">
          <el-date-picker v-model="endTime" type="date" placeholder="Pick a day"
                          :picker-options="{ disabledDate: (time) => disabledEndDate(time, null) }">
          </el-date-picker>
        </div>
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
      <div class="col-lg-5 col-sm-6" @mouseover="showBreakDown" @mouseleave="hideBreakDown">
        <chart-card :chart-data="LeadTimeForChange.data"
                    chart-id="activity-chart"
                    :color="LeadTimeForChange.color"
                    :stacked="stacked"
                    chart-title="Lead Time For Change"
                    :chart-options="chartOptions">
          :class="'border-' + LeadTimeForChange.rate"
          >
          <span slot="hover-slot" class="tooltip-custom"><i class="nc-icon nc-alert-circle-i"></i><span
            class="tooltip-custom-text">변경 리드 타임: 변경 리드 타임은 변경 리드타임입니다.</span></span>
          <span slot="title">변경 리드 타임</span>
          <badge slot="title-label" :type="LeadTimeForChange.rate">{{ LeadTimeForChange.rate }}</badge>

          <p-button slot="footer-right" aria-label="add button" :type="typeChange(LeadTimeForChange.rate)" round icon
                    size="sm">
            <i class="nc-icon nc-simple-add"></i>
          </p-button>
        </chart-card>
      </div>

      <div class="col-lg-5 col-sm-6">
        <chart-card :chart-data="DeploymentFrequency.data"
                    chart-id="emails-chart"
                    :color="DeploymentFrequency.color"
                    chart-title="Deployment Frequency"
                    :class="'border-' + DeploymentFrequency.rate">
          <span slot="hover-slot" class="tooltip-custom"><i class="nc-icon nc-alert-circle-i"></i><span
            class="tooltip-custom-text">배포 빈도 : 배포 빈도는 일주일 동안 배포한 횟수를 이야기합니다.</span></span>
          <span slot="title">배포 빈도</span>
          <badge slot="title-label" :type="DeploymentFrequency.rate">{{ DeploymentFrequency.rate }}</badge>
          <p-button slot="footer-right" aria-label="add button" :type="typeChange(DeploymentFrequency.rate)" round icon
                    size="sm">
            <i class="nc-icon nc-simple-add"></i>
          </p-button>
        </chart-card>
      </div>

      <div class="col-lg-5 col-sm-6">
        <chart-card :chart-data="ChangeFailureRate.data"
                    chart-id="active-countries-chart"
                    :color="ChangeFailureRate.color"
                    chart-title="Change Failure Rate"
                    :class="'border-' + ChangeFailureRate.rate">
          <span slot="hover-slot" class="tooltip-custom"><i class="nc-icon nc-alert-circle-i"></i><span
            class="tooltip-custom-text">평균회복시간: 변경 리드 타임은 변경 리드타임입니다.</span></span>
          <span slot="title">변경 실패율</span>
          <badge slot="title-label" :type="ChangeFailureRate.rate">{{ ChangeFailureRate.rate }}</badge>
          <p-button slot="footer-right" aria-label="add button" :type="typeChange(ChangeFailureRate.rate)" round icon
                    size="sm">
            <i class="nc-icon nc-simple-add"></i>
          </p-button>
        </chart-card>
      </div>

      <div class="col-lg-5 col-sm-6">
        <chart-card :chart-data="MTTR.data"
                    chart-id="active-countries-chart"
                    :color="MTTR.color"
                    chart-title="Mean Time To Recover"
                    :class="'border-' + MTTR.rate">
          <span slot="hover-slot" class="tooltip-custom"><i class="nc-icon nc-alert-circle-i"></i><span
            class="tooltip-custom-text">information: 변경 리드 타임은 변경 리드타임입니다.</span></span>
          <span slot="title">평균 회복시간</span>
          <badge slot="title-label" :type="MTTR.rate">{{ MTTR.rate }}</badge>
          <p-button slot="footer-right" aria-label="add button" :type="typeChange(MTTR.rate)" round icon size="sm">
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
    }
  },
  data() {
    return {
      stacked: false,
      LeadTimeForChangeDetailDataSets: [],
      LeadTimeForChange: {
        color: "#ef8156",
        rate: "seed",
        data: {
          labels: [1, 2, 3, 4, 5, 6, 7, 8, 9, 0],
          series: [542, 480, 430, 550, 530, 453, 380, 434, 568, 610]
        }
      },
      DeploymentFrequency: {
        color: "#41B883",
        rate: "fruit",
        data: {
          labels: ["12pm", "3pm", "6pm", "9pm", "12am", "3am", "6am", "9am"],
          series: [40, 500, 650, 700, 1200, 1250, 1300, 1900]
        }
      },
      ChangeFailureRate: {
        color: "#68B3C8",
        rate: "flower",
        data: {
          labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October"],
          series: [80, 78, 86, 96, 83, 85, 76, 75, 88, 90]
        }
      },
      MTTR: {
        color: "#fcc468",
        rate: "sprout",
        data: {
          labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October"],
          series: [80, 78, 86, 96, 83, 85, 76, 75, 88, 90]
        }
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
          return "#AC7088"
        case "pickupTime":
          return "#DEB6AB"
        case "reviewTime":
          return "#ECCCB2"
        case "deployTime":
          return "#F5E8C7"
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
        response = await axios.get(this.custom.defaultURL + "/api/check", {headers: setHeaderJWT()});
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
    }, async getRepositoryInfo() {
      let axiosResponse;

      try {
        axiosResponse = await axios.get(this.custom.defaultURL + "/user/repository-info", {
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
      try {
        this.getAllDoraMetric(this.dateFormat(this.startTime), this.dateFormat(this.endTime), this.selects.multiple);
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
    }, async getDoraMetric(start_time, end_time, repo_id, MetricName) {
      console.log(MetricName + "이 들어왔네")
      const Message = await axios.get(this.custom.defaultURL + "/dorametric/" + MetricName, {
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
        let totalValue = Object.values(detail).reduce((a, b) => {
          return a + b
        }, 0)
        coding_time.push(detail.codingTime || 0);
        pickup_time.push(detail.pickupTime || 0);
        review_time.push(detail.reviewTime || 0);
        deploy_time.push(detail.deployTime || 0);
        date_labels.push(this.dateFormat(start_date));
        data_series.push(totalValue);
        start_date.setDate(start_date.getDate() + 1);
      }
      this.LeadTimeForChangeDetailDataSets = [
        {
          label: 'coding time',
          data: coding_time,
          backgroundColor: this.getStackedColor("codingTime"),
        },
        {
          label: 'pickup time',
          data: pickup_time,
          backgroundColor: this.getStackedColor("codingTime"),
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
      this.LeadTimeForChange.data.labels = date_labels;
      this.LeadTimeForChange.data.series = data_series;
      this.LeadTimeForChange.rate = level;
      this.LeadTimeForChange.color = this.colorPickByLevel(level);
    },
    setDeploymentFrequency(start_date, end_date, info, level) {
      let date_labels = [];
      let data_series = [];

      while (start_date <= end_date) {
        date_labels.push(this.dateFormat(start_date));
        data_series.push(info[this.dateFormat(start_date)] || 0);
        start_date.setDate(start_date.getDate() + 1);
      }
      this.DeploymentFrequency.data.labels = date_labels;
      this.DeploymentFrequency.data.series = data_series;
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
      this.MTTR.data.labels = date_labels;
      this.MTTR.data.series = data_series;
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
      this.ChangeFailureRate.data.labels = date_labels;
      this.ChangeFailureRate.data.series = data_series;
      this.ChangeFailureRate.rate = level;
      this.ChangeFailureRate.color = this.colorPickByLevel(level);
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

.tooltip-custom-text {
  display: none;
  position: absolute;
  min-width: 20vw;
  max-width: 20vw;
  border: 5px solid;
  border-radius: 10px;
  padding: 5px;
  font-size: 1em;
  color: white;
  background: deeppink !important;
}

.tooltip-custom:hover .tooltip-custom-text {
  display: block;
}
</style>
