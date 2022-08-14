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
    }
  }
}
</script>

<style>

</style>