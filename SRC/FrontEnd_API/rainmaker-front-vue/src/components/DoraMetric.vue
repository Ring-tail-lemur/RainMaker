<template>
  <div>
    <GraphSelectInput v-bind:name="value" @setInput="setInput"></GraphSelectInput>
<!--    자식 데이터 {{value}}-->
    <div id="mainArea">
      <draw-graph v-bind:start-time="startTime" :end-time="endTime" :repo-id="repoId" name="LeadTimeForChange"></draw-graph>
      <draw-graph v-bind:start-time="startTime" :end-time="endTime" :repo-id="repoId" name="DeploymentFrequency"></draw-graph>
      <draw-graph v-bind:start-time="startTime" :end-time="endTime" :repo-id="repoId" name="ChangeFailureRate"></draw-graph>
      <draw-graph v-bind:start-time="startTime" :end-time="endTime" :repo-id="repoId" name="MTTR" ></draw-graph>
    </div>
  </div>

</template>

<script>
import GraphSelectInput from "./GraphSelectInput.vue";
import drawGraph from "./drawGraph.vue";

export default {
  name: 'DoraMetric',
  components: {
    'GraphSelectInput': GraphSelectInput,
    'draw-graph': drawGraph
  },
  data() {
    return {
      value : "",
      startTime : "",
      endTime : "",
      repoId : "",
    };
  },
  methods : {
    setInput(value, startTime, endTime, repoId) {
      this.value = value;
      this.startTime = startTime;
      this.endTime = endTime;
      this.repoId = repoId;
      console.log("넘어온 데이터 : ",this.value, this.startTime, this.endTime, this.repoId);
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

input {
  border-style: groove;
  width: 200px;
}

button {
  border-style: groove;
}
.shadow {
  box-shadow: 5px 10px 10px rgba(0,0,0,0.03);
}

#mainArea {
  vertical-align: text-bottom;
  min-height: 100%;
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
}
</style>
