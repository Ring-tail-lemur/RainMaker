<template>
  <div class="col-md-4">
    <chart-card :chart-data="emailChart"
                chart-type="Pie"
                title="Email Statistics"
                description="Last Campaign Performance"
                :chart-options="{cutoutPercentage: 70}">
      <span slot="title">싸이클 타임</span>
      <template slot="footer">
        <div class="legend">

          <i class="fa fa-circle" style="color:rgba(239,129,86,0.7)"></i> 코딩 시간
          <i class="fa fa-circle" style="color:rgba(125,217,245,0.7)"></i> 인지 시간
          <i class="fa fa-circle" style="color:rgba(55,152,109,0.7)"></i> 리뷰 시간
          <i class="fa fa-circle" style="color:rgba(203,104,252,0.7)"></i> 배포 시간
        </div>
        <hr>
        <div class="stats">
          <i class="fa fa-check"></i> 7일 동안의 지표입니다.
        </div>
      </template>
    </chart-card>
  </div>
</template>
<script>
import { Card } from 'src/components/UIComponents'
import ChartCard from 'src/components/UIComponents/Cards/ChartCard'
import PieChart from 'src/components/UIComponents/Charts/PieChart'
import DoughnutChart from "@/components/UIComponents/Charts/DoughnutChart";

const tooltipOptions = {
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
};
export default {
  components: {
    Card,
    ChartCard,
    PieChart,
    DoughnutChart
  },
  data() {
    return {
      emailChart: {
        labels: ['코딩 시간',
          '인지 시간',
          '리뷰 시간', '배포 시간'],
        datasets: [{
          label: "Emails",
          pointRadius: 0,
          pointHoverRadius: 0,
          backgroundColor: [
            'rgba(239,129,86,0.7)',
            'rgba(125,217,245,0.7)',
            'rgba(55,152,109,0.7)',
            'rgba(203,104,252,0.7)'
          ],
          borderWidth: 0,
          data: [542, 480, 430,492]
        }],
        options: {
          tooltips: tooltipOptions
        }
      },
    }
  }
}
</script>
