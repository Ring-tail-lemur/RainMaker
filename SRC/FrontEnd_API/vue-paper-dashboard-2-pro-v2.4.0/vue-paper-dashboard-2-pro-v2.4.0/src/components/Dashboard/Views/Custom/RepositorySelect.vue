<template>
  <div class="card">
    <div class="card-header">
      <h5 class="card-title">Table with Switches</h5>
    </div>
    <div class="card-body row table-full-width">
      <div class="col-sm-12">
        <el-table class="table-striped"
                  header-row-class-name="text-primary"
                  :data="tableData">
          <el-table-column type="index">

          </el-table-column>
          <el-table-column prop="name"
                           label="Name">
          </el-table-column>
          <el-table-column prop="job"
                           label="Job Position">
          </el-table-column>
          <el-table-column prop="salary"
                           align="center"
                           label="Salary">
          </el-table-column>
          <el-table-column
            align="right"
            label="Active">
            <template slot-scope="props">
              <p-switch v-model="props.row.active"></p-switch>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import PSwitch from "@/components/UIComponents/Switch";

export default {
  components: {
    PSwitch
  },
  data () {
    return {
      tableData: [{
        name: 'Andrew Mike',
        job: 'Develop',
        salary: '€ 99,225',
        active: false
      }, {
        name: 'John Doe',
        job: 'Design',
        salary: '€ 89,241',
        active: false
      }, {
        name: 'Alex Mike',
        job: 'Design',
        salary: '€ 92,144',
        active: false
      }, {
        name: 'Mike Monday',
        job: 'Marketing',
        salary: '€ 49,990',
        active: true
      },
        {
          name: 'Paul dickens',
          job: 'Communication',
          salary: '€ 69,201',
          active: true
        }],
      productsTable: [
        {
          image: 'static/img/tables/agenda.png',
          title: 'Notebook',
          subTitle: 'Most beautiful agenda for the office.',
          price: 49,
          quantity: 1
        },
        {
          image: 'static/img/tables/stylus.jpg',
          title: 'Stylus',
          subTitle: 'Design is not just what it looks like and feels like. Design is how it works.',
          price: 499,
          quantity: 2
        },
        {
          image: 'static/img/tables/evernote.png',
          title: 'Evernote iPad Stander',
          subTitle: 'A groundbreaking Retina display. All-flash architecture. Fourth-generation Intel processors.',
          price: 799,
          quantity: 1
        }
      ]
    }
  },
  name: "RepositorySelect"
}
</script>

<style>

</style>
