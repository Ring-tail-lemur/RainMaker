<template>
  <div v-if="waiting">
    <div class="row">
      <MeanTimeToRepairBar class="col-lg-6 col-sm-6"></MeanTimeToRepairBar>
      <MeanTimeToRepairBreakDownGraph class="col-lg-6 col-sm-6"></MeanTimeToRepairBreakDownGraph>
    </div>
    <div>
      <MeanTimeToRepairDetailTable></MeanTimeToRepairDetailTable>
    </div>
  </div>
  <loading-main-panel v-else="waiting"></loading-main-panel>
</template>

<script>
import CircleChartCard from 'src/components/UIComponents/Cards/CircleChartCard.vue'
import Loading from 'src/components/Dashboard/Layout/LoadingMainPanel.vue'
import LoadingMainPanel from "@/components/Dashboard/Layout/LoadingMainPanel";
import MeanTimeToRepairBar
  from "@/components/Dashboard/Views/Custom/MeanTimeTORepairComponent/MeanTimeToRepairBarGraph";
import MeanTimeToRepairBreakDownGraph from "@/components/Dashboard/Views/Custom/MeanTimeTORepairComponent/MeanTimeToRepairBreakDownGraph";
import MeanTimeToRepairDetailTable
  from "@/components/Dashboard/Views/Custom/MeanTimeTORepairComponent/MeanTimeToRepairDetailTable";

const WorldMap = () => ({
  component: import('@/components/Dashboard/Views/Maps/WorldMap.vue'),
  loading: Loading,
  delay: 200
})
export default {
  components: {
    MeanTimeToRepairBreakDownGraph,
    MeanTimeToRepairBar,
    LoadingMainPanel,
    CircleChartCard,
    MeanTimeToRepairDetailTable,
    WorldMap,
  },
  data() {
    return {
      waiting: true,
    }
  }
}
</script>
