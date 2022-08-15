<template>
  <div>
    시간 : <br>
    {{startTime}}
    {{endTime}}
    {{repoId}}
    <h3>이름 : {{name}}</h3>
    <canvas v-bind:id="name" style="width:100%;max-width:600px"></canvas>
  </div>
</template>

<script>
// import getDoragraphFunction from '@/chart/getDoragraphFunction';

export default {
  name: "drawGraph",
  props : ['startTime', 'endTime', 'repoId', 'name'],
  data() {
    return {
      value : "",
    }
  },
  methods: {
    onClick() {
      console.log(this.name, "버튼 클릭");
    },
    onEmit() {
      this.$emit("setInput", this.startTime, this.endTime, this.repoId);
    },

  },
  mounted() {
    // getDoragraphFunction.initChart(this.name, [1,2,3,4,5], [1,2,3,4,5]);
    // this.initChart(this.name, [1,2,3,4,5], [1,2,3,4,5]);
  }
}
</script>

<style>

</style>