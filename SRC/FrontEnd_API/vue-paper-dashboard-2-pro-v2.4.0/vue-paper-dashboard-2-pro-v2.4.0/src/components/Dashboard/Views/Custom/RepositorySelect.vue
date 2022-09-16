<template>
  <div class="card">
    <div class="card-header">
      <h5 class="card-title">Table with Switches</h5>
    </div>
    <div class="card-body row table-full-width">
      <div class="col-sm-12">
        <el-table class="table-striped"
                  header-row-class-name="text-primary"
                  fit="true"
                  :data="tableData">
          <el-table-column type="index">

          </el-table-column>
          <el-table-column prop="organization"
                           label="그룹명">
          </el-table-column>
          <el-table-column prop="repository"
                           label="리포지토리명">
          </el-table-column>
          <el-table-column prop="pushed_at"
                           align="center"
                           label="마지막사용">
          </el-table-column>
          <el-table-column
            align="right"
            label="Active">
            <template slot-scope="props">
              <p-switch v-model="props.row.active"></p-switch>
            </template>
          </el-table-column>
        </el-table>
        <button>aaaaa</button>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import {Table, TableColumn} from 'element-ui'
import PSwitch from 'src/components/UIComponents/Switch.vue'
Vue.use(Table)
Vue.use(TableColumn)
export default {
  components: {
    PSwitch
  },
  data () {
    return {
      name: "RepositorySelect",
      tableData: [{
        id : '123141121111',
        organization: '인혁',
        repository: 'RainMaker',
        pushed_at: '2022-06-08T10:06:50Z',
        active: false
      }, {
        id:"123141132222",
        organization: "종현",
        repository: "test-for-fake-project",
        pushed_at : "2022-06-08T10:06:50Z",
        active: false
      }, {
        id:"123141143333",
        organization: "동인",
        repository: "gugudan",
        pushed_at : "2022-06-08T10:06:50Z",
        active: false
      }, {
        organization: 'Mike Monday',
        repository: 'Marketing',
        pushed_at: '€ 49,990',
        active: true
      },
        {
          organization: 'Paul dickens',
          repository: 'Communication',
          pushed_at: '€ 69,201',
          active: true
        }]
    }
  }
}
</script>

<style>

</style>
