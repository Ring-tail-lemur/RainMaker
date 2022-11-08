<template>
  <div>
    <div>
      <div class="col-lg-12 col-md-12">
        <time-line type="simple" class="timeline-margin">

          <time-line-item badgeType="danger" badgeIcon="nc-icon nc-share-66" :inverted="true"
                          v-for="releaseData in releaseDataSet" :style="{ 'margin-bottom' : releaseData[0].margin }">
            <div slot="Description" class="datetime-title">{{releaseData[0].releaseDate}}</div>
            <div slot="content">
              <el-table :data="releaseData" header-row-class-name="text-primary">
                <el-table-column prop="version" label="버전명"></el-table-column>
                <el-table-column prop="commitCount" label="커밋 갯수"></el-table-column>
                <el-table-column prop="codeChange" label="코드 변화량"></el-table-column>
                <el-table-column prop="releaseTime" label="배포 시간"></el-table-column>
              </el-table>
            </div>
          </time-line-item>

        </time-line>
      </div>
    </div>
  </div>
</template>
<script>
import TaskList from '@/components/Dashboard/Views/Dashboard/Widgets/TaskList'
import {TimeLine, TimeLineItem, Card, Dropdown, Badge} from 'src/components/UIComponents'
import {Table, TableColumn} from 'element-ui'

export default {
  components: {
    TaskList,
    TimeLine,
    TimeLineItem,
    Card,
    Dropdown,
    Badge,
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
  },
  data() {
    return {
      releaseDataSet: [
        [{
          releaseDate : '2022-08-04',
          version: 'v1.0.0',
          commitCount: '4',
          codeChange: '284',
          releaseTime: '2022-08-04, 14시 32분',
          margin: '0px'
        }],
        [{
          releaseDate : '2022-08-04',
          version: 'v1.0.1',
          commitCount: '10',
          codeChange: '184',
          releaseTime: '2022-08-04, 14시 32분',
          margin: '50px'
        }],
        [{
          releaseDate : '2022-08-05',
          version: 'v1.0.2',
          commitCount: '10',
          codeChange: '184',
          releaseTime: '2022-08-04, 14시 32분',
          margin: '650px'
        }],
        [{
          releaseDate : '2022-08-06',
          version: 'v1.0.2',
          commitCount: '10',
          codeChange: '184',
          releaseTime: '2022-08-04, 14시 32분',
          margin: '0px'
        }],
      ],
    }
  }
}
</script>
<style>
.datetime-title {
  display: inline-block;
  transform: translateX(-100px);
  width: 100px;
  border: 1px solid #a8a8a8;
  border-radius: 10px;
  text-align: center;
  margin-top: 28px;
  background-color: #fff;
  font-size: 17px;
}

.timeline-margin {
  margin-left: 100px;
}
</style>
