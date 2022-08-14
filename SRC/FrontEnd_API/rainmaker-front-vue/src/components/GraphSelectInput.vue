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
    }
  },
  methods: {
    onClick() {
      console.log(this.name, "버튼 클릭");
    },
    onEmit() {
      this.$emit("setInput", this.value, this.startTime, this.endTime, this.repoId);
    }
  }
}
</script>

<style>

</style>