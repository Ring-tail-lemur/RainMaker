<template>
  <div>
    <div class="row">
      <div class="col-md-3">
        <h4 class="card-title">시작 시간</h4>
        <div class="form-group">
          <el-date-picker v-model="startTime" type="date" placeholder="Pick a day"
                          :picker-options="pickerOptions1">
          </el-date-picker>
        </div>
      </div>
      <div class="col-md-3">
        <h4 class="card-title">끝 시간</h4>
        <div class="form-group">
          <el-date-picker v-model="endTime" type="date" placeholder="Pick a day"
                          :picker-options="pickerOptions1">
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
            <el-option v-for="option in selects.countries"
                       class="select-primary"
                       :value="option.value"
                       :label="option.label"
                       :key="option.label">
            </el-option>
          </el-select>
        </fg-input>
      </div>
      <div class="col-md-2">
        <br><br><br>
        <p-button >제출하기</p-button>
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
  data () {
    return {
      statsCards: [
        {
          type: 'warning',
          icon: 'nc-icon nc-globe',
          title: 'Capacity',
          value: '105GB',
          footerText: 'Updated now',
          footerIcon: 'nc-icon nc-refresh-69'
        },
        {
          type: 'success',
          icon: 'nc-icon nc-money-coins',
          title: 'Revenue',
          value: '$1,345',
          footerText: 'Last day',
          footerIcon: 'nc-icon nc-calendar-60'
        },
        {
          type: 'danger',
          icon: 'nc-icon nc-vector',
          title: 'Errors',
          value: '23',
          footerText: 'In the last hour',
          footerIcon: 'nc-icon nc-bell-55'
        },
        {
          type: 'info',
          icon: 'nc-icon nc-favourite-28',
          title: 'Followers',
          value: '+45',
          footerText: 'Updated now',
          footerIcon: 'nc-icon nc-refresh-69'
        }
      ],
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
        countries: [{value: 'Bahasa Indonesia', label: 'Bahasa Indonesia'},
          {value: 'Bahasa Melayu', label: 'Bahasa Melayu'},
          {value: 'Català', label: 'Català'},
          {value: 'Dansk', label: 'Dansk'},
          {value: 'Deutsch', label: 'Deutsch'},
          {value: 'English', label: 'English'},
          {value: 'Español', label: 'Español'},
          {value: 'Eλληνικά', label: 'Eλληνικά'},
          {value: 'Français', label: 'Français'},
          {value: 'Italiano', label: 'Italiano'},
          {value: 'Magyar', label: 'Magyar'},
          {value: 'Nederlands', label: 'Nederlands'},
          {value: 'Norsk', label: 'Norsk'},
          {value: 'Polski', label: 'Polski'},
          {value: 'Português', label: 'Português'},
          {value: 'Suomi', label: 'Suomi'},
          {value: 'Svenska', label: 'Svenska'},
          {value: 'Türkçe', label: 'Türkçe'},
          {value: 'Íslenska', label: 'Íslenska'},
          {value: 'Čeština', label: 'Čeština'},
          {value: 'Русский', label: 'Русский'},
          {value: 'ภาษาไทย', label: 'ภาษาไทย'},
          {value: '中文 (简体)', label: '中文 (简体)'},
          {value: 'W">中文 (繁體)', label: 'W">中文 (繁體)'},
          {value: '日本語', label: '日本語'},
          {value: '한국어', label: '한국어'}],
        multiple: 'ARS'
      },



    }
  }
}

</script>
<style>

</style>
