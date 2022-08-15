<template>
  <div>
    <button @click="onClick">console</button>
    시작시간 <input type="text" v-model="startTime"><br>
    끝시간 <input type="text" v-model="endTime"><br>
    RepoId <input type="text" v-model="repoId"><br>
    <button @click="onEmit">{{name}} Emit 버튼</button>
  </div>
</template>

<script>
import getDoragraphFunction from '@/chart/getDoragraphFunction';

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

      const [chart1, chart2, chart3, chart4] = await getDoragraphFunction.chartChange(this.startTime, this.endTime, this.repoId);
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