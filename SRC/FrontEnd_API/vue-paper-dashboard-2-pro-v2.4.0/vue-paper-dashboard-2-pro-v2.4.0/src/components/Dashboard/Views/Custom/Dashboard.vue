<template>
  <div>
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
      <div class="col-lg-5 col-sm-6" >
        <chart-card :chart-data="LeadTimeForChange.data"
                    chart-id="activity-chart"
                    :color="LeadTimeForChange.color"
                    chart-title="TOTAL EARNINGS IN LAST TEN QUARTERS">
          <span slot="title">리드 타임</span>
          <badge slot="title-label" :type="LeadTimeForChange.rate">{{LeadTimeForChange.rate}}</badge>


        </chart-card>
      </div>

      <div class="col-lg-5 col-sm-6">
        <chart-card :chart-data="DeploymentFrequency.data"
                    chart-id="emails-chart"
                    :color="DeploymentFrequency.color" chart-title="TOTAL SUBSCRIPTIONS IN LAST 7 DAYS">
          <span slot="title">배포 빈도</span>
          <badge slot="title-label" :type="DeploymentFrequency.rate">{{DeploymentFrequency.rate}}</badge>

        </chart-card>
      </div>

      <div class="col-lg-5 col-sm-6">
        <chart-card :chart-data="ChangeFailureRate.data"
                    chart-id="active-countries-chart"
                    :color="ChangeFailureRate.color" chart-title="Active Countries">
          <span slot="title">변화실패율</span>
          <badge slot="title-label" :type="ChangeFailureRate.rate">{{ChangeFailureRate.rate}}</badge>

        </chart-card>
      </div>

      <div class="col-lg-5 col-sm-6">
        <chart-card :chart-data="MTTR.data"
                    chart-id="active-countries-chart"
                    :color="MTTR.color" chart-title="Active Countries">
          <span slot="title">평균회복시간</span>
          <badge slot="title-label" :type="MTTR.rate">{{MTTR.rate}}</badge>

        </chart-card>
      </div>
    </div>




    <div class="row">
      <div class="col-lg-3 col-sm-6">
        <circle-chart-card :percentage="70"
                           chart-id="email-statistics-chart"
                           title="Email Statistics"
                           description="Last Campaign Performance"
                           color="#4acccd">
          <template slot="footer">
            <div class="legend">
              <i class="fa fa-circle text-info"></i> Open
            </div>
            <hr>
            <div class="stats">
              <i class="fa fa-calendar"></i> Number of emails sent
            </div>
          </template>
        </circle-chart-card>
      </div>

      <div class="col-lg-3 col-sm-6">
        <circle-chart-card :percentage="34"
                           chart-id="new-visitors-chart"
                           title="New Visitators"
                           description="Out Of Total Number"
                           color="#fcc468">
          <template slot="footer">
            <div class="legend">
              <i class="fa fa-circle text-warning"></i> Visited
            </div>
            <hr>
            <div class="stats">
              <i class="fa fa-check"></i> Campaign sent 2 days ago
            </div>
          </template>
        </circle-chart-card>
      </div>

      <div class="col-lg-3 col-sm-6">
        <circle-chart-card :percentage="80"
                           title="Orders"
                           chart-id="orders-chart"
                           description="Total Number"
                           color="#f17e5d">
          <template slot="footer">
            <div class="legend">
              <i class="fa fa-circle text-danger"></i> Completed
            </div>
            <hr>
            <div class="stats">
              <i class="fa fa-clock-o"></i> Updated 3 minutes ago
            </div>
          </template>
        </circle-chart-card>
      </div>

      <div class="col-lg-3 col-sm-6">
        <circle-chart-card :percentage="11"
                           title="Subscriptions"
                           description="Our Users"
                           color="#66615b">
          <template slot="footer">
            <div class="legend">
              <i class="fa fa-circle text-secondary"></i> Ended
            </div>
            <hr>
            <div class="stats">
              <i class="fa fa-history"></i> Total users
            </div>
          </template>
        </circle-chart-card>
      </div>
    </div>

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
import pageCheckAndChange from "@/util/pageCheckAndChange";

const WorldMap = () => ({
  component: import('./../Maps/WorldMap.vue'),
  loading: Loading,
  delay: 200
})

export default {
  components: {
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
      if(this.startTime != '') {
        if( this.endTime < this.startTime) {
          alert("끝시간은 시작시간보다 커야합니다");
          this.endTime = '';
        }
      }
    },
    startTime() {
      if(this.endTime != '') {
        if( this.endTime < this.startTime) {
          alert("시작시간은 끝시간보다 작아야합니다");
          this.startTime = '';
        }
      }
    }
  },
  data () {
    return {
      LeadTimeForChange: {
        color : "#ef8156",
        rate : "seed",
        data: {
          labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct"],
          series: [542, 480, 430, 550, 530, 453, 380, 434, 568, 610]
        }
      },
      DeploymentFrequency: {
        color : "#41B883",
        rate : "fruit",
        data: {
          labels: ["12pm", "3pm", "6pm", "9pm", "12am", "3am", "6am", "9am"],
          series: [40, 500, 650, 700, 1200, 1250, 1300, 1900]
        }
      },
      ChangeFailureRate: {
        color : "#68B3C8",
        rate : "flower",
        data: {
          labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October"],
          series: [80, 78, 86, 96, 83, 85, 76, 75, 88, 90]
        }
      },
      MTTR: {
        color : "#fcc468",
        rate : "sprout",
        data: {
          labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October"],
          series: [80, 78, 86, 96, 83, 85, 76, 75, 88, 90]
        }
      },
      startTime: '',
      endTime : '',

      selects: {
        simple: '',
        repositories: [
          {value: '510731046', label: 'RainMaker'},
          {value: '517528822', label: 'test-for-fake-project'},
          {value: '193281821', label: 'gugudan'},
          ],
        multiple: 'ARS'
      },

    }
  },
  methods : {
    async getRepositoryInfo() {
      let axiosResponse;

      try {
        axiosResponse = await axios.get(this.custom.defaultURL + "/user/repository-info", {
          headers: setHeaderJWT()
        });
      } catch (e) {
        pageCheckAndChange(e, this);
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
      if(start_time === undefined || end_time === undefined || repo_id === undefined) {
        alert("잘못 입력하셨습니다.");
        return;
      }
      this.getDoraMetric(start_time, end_time, repo_id,"lead-time-for-change");
      this.getDoraMetric(start_time, end_time, repo_id,"time-to-restore-service");
      this.getDoraMetric(start_time, end_time, repo_id,"change-failure-rate");
      this.getDoraMetric(start_time, end_time, repo_id,"deployment-frequency");
    },
    async getDoraMetric(start_time, end_time, repo_id, MetricName) {
      const Message = await axios.get(this.custom.defaultURL + "/dorametric/" + MetricName, {
        params : {
          start_time : start_time,
          end_time : end_time,
          repo_id : repo_id.toString()
        }
      })

      let BodyData = Message.data;
      let info;
      if(BodyData.hasOwnProperty('leadTimeForChangeMap')){
        info = BodyData.leadTimeForChangeMap;
      } else if(BodyData.hasOwnProperty('changeFailureRateMap')) {
        info = BodyData.changeFailureRateMap;
      } else if(BodyData.hasOwnProperty('deploymentFrequencyMap')) {
        info = BodyData.deploymentFrequencyMap;
      } else if(BodyData.hasOwnProperty('timeToRestoreServiceMap')) {
        info = BodyData.timeToRestoreServiceMap;
      }


      let date_arr = [];
      let average_time = [];

      const st = new Date(start_time);
      const en = new Date(end_time);


      while(st <= en) {
        date_arr.push(this.dateFormat(st));
        if( info[this.dateFormat(st)] ) {
          average_time.push(info[this.dateFormat(st)]);
        } else {
          average_time.push(0);
        }
        st.setDate(st.getDate() + 1);
      }

      const level = BodyData.level.toLowerCase();
      if(MetricName === "lead-time-for-change"){
        this.LeadTimeForChange.data.labels = date_arr;
        this.LeadTimeForChange.data.series = average_time;
        this.LeadTimeForChange.rate = level;
        this.LeadTimeForChange.color = this.colorPickByLevel(level);
      } else if(MetricName === "time-to-restore-service") {
        this.MTTR.data.labels = date_arr;
        this.MTTR.data.series = average_time;
        this.MTTR.rate = level;
        this.MTTR.color = this.colorPickByLevel(level);
      } else if(MetricName === "change-failure-rate") {
        this.ChangeFailureRate.data.labels = date_arr;
        this.ChangeFailureRate.data.series = average_time;
        this.ChangeFailureRate.rate = level;
        this.ChangeFailureRate.color = this.colorPickByLevel(level);
      } else if(MetricName === "deployment-frequency") {
        this.DeploymentFrequency.data.labels = date_arr;
        this.DeploymentFrequency.data.series = average_time;
        this.DeploymentFrequency.rate = level;
        this.DeploymentFrequency.color = this.colorPickByLevel(level);
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
      if(level == "seed") {
        return "#ef8156";
      } else if(level == "sprout") {
        return "#fcc468";
      } else if(level == "flower") {
        return "#68B3C8";
      } else if(level == "fruit") {
        return "#41B883";
      }
    }
  },
  async created() {
    let Today = new Date();
    const FormatToday = this.dateFormat(Today);
    Today.setMonth(Today.getMonth() -1);
    const FormatLastMonth = this.dateFormat(Today);

    const repositories = await this.getRepositoryInfo();
    this.selects.repositories = repositories;

    const repositoryArr = [];

    repositories.forEach((repository) => {
      repositoryArr.push(repository['repositoryId']);
    });

    this.getAllDoraMetric(FormatLastMonth, FormatToday, repositoryArr);

  }
}

</script>
<style>

</style>
