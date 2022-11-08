<template>
  <div>
    <div>
      <div class="col-lg-12 col-md-12">
        <time-line>
          <time-line-item badgeType="danger" badgeIcon="nc-icon nc-single-copy-04" :inverted="false">
            <badge slot="header" type="danger">v1.0.0</badge>

            <div slot="content">
              <el-table :data="[{
                    name: 'Andrew Mike',
                    job: 'Develop',
                    salary: '€ 99,225',
                    active: false
                  }, {
                    name: 'John Doe',
                    job: 'Design',
                    salary: '€ 89,241',
                    active: false
                  }]" header-row-class-name="text-primary">
                <el-table-column type="index">

                </el-table-column>
                <el-table-column prop="name"
                                 label="배포 시간">
                </el-table-column>
                <el-table-column prop="job"
                                 label="Job Position">
                </el-table-column>
                <el-table-column prop="salary"
                                 label="Job Position">
                </el-table-column>
                <el-table-column prop="active"
                                 label="Job Position">
                </el-table-column>
              </el-table>
            </div>

            <h6 slot="footer">
              <i class="fa fa-block-o"></i>
              11 hours ago via Twitter
            </h6>
          </time-line-item>

          <time-line-item class="timeline-inverted" badgeType="success" badgeIcon="nc-icon nc-sun-fog-29" :inverted="true">
            <badge slot="header" type="success">Another Title</badge>
            <p slot="content">
              Thank God for the support of my wife and real friends. I also wanted to point out that it’s the first
              album
              to go number 1 off of streaming!!! I love you Ellen and also my number one design rule of anything I do
              from
              shoes to music to homes is that Kim has to like it....</p>
          </time-line-item>

          <time-line-item class="timeline-inverted" badgeType="info" badgeIcon="nc-icon nc-world-2">
            <badge slot="header" type="info">Another Title</badge>
            <div slot="content">
              <p>
                Called I Miss the Old Kanye That’s all it was Kanye And I love you like Kanye loves Kanye Famous viewing
                @
                Figueroa and 12th in downtown LA 11:10PM</p>
              <p>
                What if Kanye made a song about Kanye Royère doesn't make a Polar bear bed but the Polar bear couch is
                my
                favorite piece of furniture we own It wasn’t any Kanyes Set on his goals Kanye</p>
              <hr>
              <drop-down class="btn-group">
                <p-button slot-scope="{isOpen}" slot="title" type="info" round class="dropdown-toggle"
                          :aria-expanded="isOpen">
                  <i class="nc-icon nc-settings"></i> &nbsp;
                </p-button>
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <a class="dropdown-item" href="#">Something else here</a>
              </drop-down>
            </div>
          </time-line-item>
        </time-line>
      </div>
    </div>
  </div>
</template>
<script>
import TaskList from '@/components/Dashboard/Views/Dashboard/Widgets/TaskList'
import { TimeLine, TimeLineItem, Card, Dropdown, Badge } from 'src/components/UIComponents'
import { Table, TableColumn } from 'element-ui'

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
      employeeData: [
        { id: 1, name: "Dakota Rice", salary: "$36,738", country: "Niger" },
        { id: 2, name: "Minerva Hooper", salary: "$23,789", country: "Curaçao" },
        { id: 3, name: "Sage Rodriguez", salary: "$56,142", country: "Netherlands" },
        { id: 4, name: "Philip Chaney", salary: "$38,735", country: "Korea, South" },
      ]
    }
  }
}
</script>
<style>
</style>
