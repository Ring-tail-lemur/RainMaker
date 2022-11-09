<template>
  <div class="row">
    <div class="col-md-12">

    </div>
    <div class="col-md-12 card">
      <h4 class="Detail-title">변경 리드 타임 세부 사항</h4>
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
          minWidth: 150
        },
        {
          prop: 'codingTime',
          label: '코딩 시간',
          minWidth: 90
        },
        {
          prop: 'pickupTime',
          label: '인지 시간',
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
          prop: 'codeChange',
          label: '코드변경',
          minWidth: 70
        },
        {
          prop: 'reviewSize',
          label: '리뷰갯수',
          minWidth: 70
        },
        {
          prop: 'branches',
          label: '브랜치 체류시간',
          minWidth: 150
        }
      ],
      tableData: users
    }
  },
  methods: {
    handleLike(index, row) {
      alert(`Your want to like ${row.name}`)
    },
    handleEdit(index, row) {
      alert(`Your want to edit ${row.name}`)
    },
    handleDelete(index, row) {
      let indexToDelete = this.tableData.findIndex((tableRow) => tableRow.id === row.id)
      if (indexToDelete >= 0) {
        this.tableData.splice(indexToDelete, 1)
      }
    }, dateFormat(date) {
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
      await this.getLeadTimeForChangeDetail(FormatLastMonth, FormatToday, repositoryArr);
    },
    async getRepositoryInfo() {
      let axiosResponse;
      axiosResponse = await axios.get(this.custom.defaultURL + "/api/user/repositories", {
        headers: setHeaderJWT()
      });
      return axiosResponse.data;
    },
    async getLeadTimeForChangeDetail(start_time, end_time, repo_ids,) {
      let response = await axios.get(this.custom.defaultURL + "/api/dorametric/lead-time-for-change/cycle-time-detail/sources",
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
      this.tableData = responseData.map(x => {
        let urlList = x.pullRequestUrl.split('/');
        let entries = Object.entries(x.branchStayDuration).map((value, index) => {
          value[1] = value[1]+'분'
          if (value[1] == '-1분') {
            value[1] = '현재 branch';
          }
          return value
        });
        let branchList = JSON.stringify(Object.fromEntries(entries))
          .replace('{', '')
          .replace('}', '')
          .replaceAll('"', '')
          .replaceAll(':', ' : ')
          .split(',');
        let temp = {
          url: <a href={`${x.pullRequestUrl}`}>{urlList[urlList.length - 3] + '#' + urlList[urlList.length - 1]}</a>,
          codingTime: x.cycleTimeDetailDto.codingAverageTime,
          pickupTime: x.cycleTimeDetailDto.pickupAverageTime,
          reviewTime: x.cycleTimeDetailDto.reviewAverageTime,
          deployTime: x.cycleTimeDetailDto.deployAverageTime,
          codeChange: x.codeChange,
          reviewSize: x.reviewSize,
          branches: <div>
            <p v-if="branchList[0]" style="margin:0;">{branchList[0]}</p>
            <p v-if="branchList[1]" style="margin:0;">{branchList[1]}</p>
            <p v-if="branchList[2]" style="margin:0;">{branchList[2]}</p>
            <p v-if="branchList[3]" style="margin:0;">{branchList[3]}</p>
            <p v-if="branchList[4]" style="margin:0;">{branchList[4]}</p>
            <p v-if="branchList[5]" style="margin:0;">{branchList[5]}</p>
            <p v-if="branchList[6]" style="margin:0;">{branchList[6]}</p>
            <p v-if="branchList[7]" style="margin:0;">{branchList[7]}</p>
            <p v-if="branchList[8]" style="margin:0;">{branchList[8]}</p>
            <p v-if="branchList[9]" style="margin:0;">{branchList[9]}</p>
            <p v-if="branchList[10]" style="margin:0;">{branchList[10]}</p>
          </div>

        }
        // temp.url.data.directives[0].value = this.a;
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
<style lang="scss">
.el-table .td-actions {
  button.btn {
    margin-right: 5px;
  }
}
</style>
