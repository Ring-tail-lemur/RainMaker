<template>
  <div class="row">
    <div class="col-md-12">

    </div>
    <div class="col-md-12 card">
      <h4 class="Detail-title">평균 회복 시간 세부 사항</h4>
      <div class="card-header">
      </div>
      <div class="card-body row">
        <div class="col-sm-6">
          <el-select
            class="select-default"
            v-model="pagination.perPage"
            placeholder="Per page">
            <el-option
              class="select-default"
              v-for="item in pagination.perPageOptions"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </div>
        <div class="col-sm-6">
          <div class="pull-right">
            <fg-input class="input-sm"
                      placeholder="Search"
                      v-model="searchQuery"
                      addon-right-icon="nc-icon nc-zoom-split">
            </fg-input>
          </div>
        </div>
        <div class="col-sm-12 mt-2">
          <el-table class="table-striped"
                    :data="queriedData"
                    border
                    style="width: 100%">
            <el-table-column v-for="column in tableColumns"
                             :key="column.label"
                             :min-width="column.minWidth"
                             :prop="column.prop"
                             :label="column.label">
            </el-table-column>
          </el-table>
        </div>
        <div class="col-sm-6 pagination-info">
          <p class="category">{{ total }}중 {{ from + 1 }} 부터 {{ to }} 까지</p>
        </div>
        <div class="col-sm-6">
          <p-pagination class="pull-right"
                        v-model="pagination.currentPage"
                        :per-page="pagination.perPage"
                        :total="pagination.total">
          </p-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import {Option, Select, Table, TableColumn} from 'element-ui'
import PPagination from 'src/components/UIComponents/Pagination.vue'
import users from '@/components/Dashboard/Views/Tables/users'
import axios from "axios";
import setHeaderJWT from "@/api/setHeaderJWT";

Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Select)
Vue.use(Option)
export default {
  name: "MeanTimeToRepairDetailTable",
  components: {
    PPagination
  },
  computed: {
    pagedData() {
      return this.tableData.slice(this.from, this.to)
    },
    /***
     * Searches through table data and returns a paginated array.
     * Note that this should not be used for table with a lot of data as it might be slow!
     * Do the search and the pagination on the server and display the data retrieved from server instead.
     * @returns {computed.pagedData}
     */
    queriedData() {
      if (!this.searchQuery) {
        this.pagination.total = this.tableData.length
        return this.pagedData
      }
      let result = this.tableData
        .filter((row) => {
          let isIncluded = false
          for (let key of this.propsToSearch) {
            let rowValue = row[key].toString()
            if (rowValue.includes && rowValue.includes(this.searchQuery)) {
              isIncluded = true
            }
          }
          return isIncluded
        })
      this.pagination.total = result.length
      return result.slice(this.from, this.to)
    },
    to() {
      let highBound = this.from + this.pagination.perPage
      if (this.total < highBound) {
        highBound = this.total
      }
      return highBound
    },
    from() {
      return this.pagination.perPage * (this.pagination.currentPage - 1)
    },
    total() {
      this.pagination.total = this.tableData.length
      return this.tableData.length
    }
  },
  data() {
    return {
      a: "zzz",
      pagination: {
        perPage: 5,
        currentPage: 1,
        perPageOptions: [5, 10, 25, 50],
        total: 0
      },
      searchQuery: '',
      propsToSearch: ['url'],
      tableColumns: [
        {
          prop: 'url',
          label: 'PR URL',
          minWidth: 180
        },
        {
          prop: 'startTime',
          label: '수정 시작 시간',
          minWidth: 90
        },
        {
          prop: 'codingTime',
          label: '코딩 시간',
          minWidth: 90
        },
        {
          prop: 'pickupTime',
          label: '리뷰 시작 시간',
          minWidth: 90
        },
        {
          prop: 'reviewTime',
          label: '리뷰 시간',
          minWidth: 90
        },
        {
          prop: 'deployTime',
          label: '배포 시간',
          minWidth: 90
        },
        {
          prop: 'convinceTime',
          label: '검증 시간',
          minWidth: 90
        },
        {
          prop: 'mttr',
          label: '총 회복 시간',
          minWidth: 90
        }
      ],
      tableData: ""
    }
  },
  methods: {
    dateFormat(date) {
      let month = date.getMonth() + 1;
      let day = date.getDate();

      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;

      return date.getFullYear() + '-' + month + '-' + day;
    },
    async getTableData(showDate) {
      let Today = new Date();
      const FormatToday = this.dateFormat(Today);
      Today.setDate(Today.getDate() - showDate);
      const FormatLastMonth = this.dateFormat(Today);
      const repositories = await this.getRepositoryInfo();
      const repositoryArr = [];
      repositories.forEach((repository) => {
        repositoryArr.push(repository['repositoryId']);
      });
      await this.getMeanTimeToRepairDetail(FormatLastMonth, FormatToday, repositoryArr);
    },
    async getRepositoryInfo() {
      let axiosResponse;
      axiosResponse = await axios.get(this.custom.defaultURL + "/api/user/repositories", {
        headers: setHeaderJWT()
      });
      return axiosResponse.data;
    },
    async getMeanTimeToRepairDetail(start_time, end_time, repo_ids) {
      let response = await axios.get(this.custom.defaultURL + "/api/dorametric/time-to-restore-service/source",
        {
          headers: setHeaderJWT(),
          params: {
            start_time: start_time,
            end_time: end_time,
            repo_id: repo_ids.toString()
          }
        }
      );
      let responseData = response.data.sort(function(a,b){
        if (a.pullRequestUrl > b.pullRequestUrl) {
          return 1;
        }
        return -1;
      })
      console.log(responseData)
      this.tableData = responseData.map(x => {
        let urlList = x.url.split('/');
        let temp = {
          url: <a href={`${x.pullRequestUrl}`}>{urlList[urlList.length - 3] + '#' + urlList[urlList.length - 1] + '(issue)'}</a>,
          startTime: (x.startTime < 0 ? 0 : x.startTime) + '분',
          codingTime: (x.codingTime < 0 ? 0 : x.codingTime) + '분',
          pickupTime: (x.pickupTime < 0 ? 0 : x.pickupTime) + '분',
          reviewTime: (x.reviewTime < 0 ? 0 : x.reviewTime) + '분',
          deployTime: (x.deployTime < 0 ? 0 : x.deployTime) + '분',
          convinceTime: (x.convinceTime < 0 ? 0 : x.convinceTime) + '분',
          mttr: (x.mttr < 0 ? 0 : x.mttr) + '분',
        }
        return temp
      })

      console.log(this.tableData)
    }
  },
  created() {
    this.getTableData(14)
  }
}
</script>

<style scoped>

</style>
