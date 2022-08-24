<template>
  <div style="flex: 2">
<!--    <button @click="onClick">console</button>-->
    <input type="text" v-model="startTime" style="margin-right: 10px; border-radius: 5px" placeholder="시작시간(ex.2022-08-01)">
    <input type="text" v-model="endTime" style="margin-right: 10px; border-radius: 5px" placeholder="끝시간(ex.2022-08-30)">
    <input type="text" v-model="repoId" style="margin-right: 10px; border-radius: 5px" placeholder="리포지토리 아이디">
    <button @click="onEmit" style="background-color: #9ca3af; border-radius: 3px">{{name}} ENTER</button>
  </div>
</template>

<script>
import chartChange from './test/chart/getDoragraphFunction.js'

export default {
  name: "GrapeSelect",
  props : {
    name : String,
  },
  data() {
    return {
      value : "",
      startTime : "",
      endTime : "",
      repoId : "",
      chart1 : null,
      chart2 : null,
      chart3 : null,
      chart4 : null
    }
  },
  methods: {
    onClick() {
      console.log(this.name, "버튼 클릭");
    },
    async onEmit() {

      if(this.chart1) {
        this.chart1.destroy();
        this.chart2.destroy();
        this.chart3.destroy();
        this.chart4.destroy();
      }
      this.$emit("setInput", this.value, this.startTime, this.endTime, this.repoId);

      const [chart1, chart2, chart3, chart4] = await chartChange(this.startTime, this.endTime, this.repoId);
      this.chart1 = await chart1;
      this.chart2 = await chart2;
      this.chart3 = await chart3;
      this.chart4 = await chart4;


    }
  }
}
</script>

<style>
canvas {
  width:100%;
  min-width:600px;
}
</style>