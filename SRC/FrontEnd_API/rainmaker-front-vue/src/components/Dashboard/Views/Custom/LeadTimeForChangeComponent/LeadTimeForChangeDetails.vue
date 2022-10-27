<template>
  <div v-if="waiting">
    <div class="row">
      <lead-time-for-change-bar class="col-lg-6 col-sm-6"></lead-time-for-change-bar>
      <cycle-time-circle-graph class="col-lg-6 col-sm-6"></cycle-time-circle-graph>
    </div>
  </div>
  <loading-main-panel v-else="waiting"></loading-main-panel>
</template>
<script>
import CircleChartCard from 'src/components/UIComponents/Cards/CircleChartCard.vue'
import Loading from 'src/components/Dashboard/Layout/LoadingMainPanel.vue'
import LoadingMainPanel from "@/components/Dashboard/Layout/LoadingMainPanel";
import LeadTimeForChangeBar from "@/components/Dashboard/Views/Custom/LeadTimeForChangeComponent/LeadTimeForChangeBarGraph";
import CycleTimeCircleGraph from "@/components/Dashboard/Views/Custom/LeadTimeForChangeComponent/CycleTimeCircleGraph";
const WorldMap = () => ({
  component: import('@/components/Dashboard/Views/Maps/WorldMap.vue'),
  loading: Loading,
  delay: 200
})
export default {
  components: {
    CycleTimeCircleGraph,
    LeadTimeForChangeBar,
    LoadingMainPanel,
    CircleChartCard,
    WorldMap,
  },
  data() {
    return {
      waiting: true,
    }
  }
}
</script>
