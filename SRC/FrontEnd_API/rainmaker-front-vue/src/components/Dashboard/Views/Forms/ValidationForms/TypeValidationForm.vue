<template>
  <ValidationObserver v-slot="{ handleSubmit }">
    <form @submit.prevent="handleSubmit(submit)">
      <card class="form-horizontal">
        <h4 slot="header" class="card-title">
          Type Validation
        </h4>

        <div class="row">
          <label class="col-sm-2 col-form-label">Required Text</label>
          <div class="col-sm-7">
            <ValidationProvider
              name="required"
              rules="required"
              v-slot="{ passed, failed }"
            >
              <fg-input type="text"
                        :error="failed ? 'The Text field is required': null"
                        :hasSuccess="passed"
                        name="requiredText"
                        v-model="required">
              </fg-input>
            </ValidationProvider>
          </div>
          <div class="col-sm-3 label-on-right">
            <code>required:true</code>
          </div>
        </div>

        <div class="row">
          <label class="col-sm-2 col-form-label">Email</label>
          <div class="col-sm-7">
            <ValidationProvider
              name="email"
              rules="required|email"
              v-slot="{ passed, failed }"
            >
              <fg-input type="text"
                        :error="failed ? 'The Email field is required': null"
                        :hasSuccess="passed"
                        name="email"
                        v-model="email">
              </fg-input>
            </ValidationProvider>
          </div>
          <div class="col-sm-3 label-on-right">
            <code>email:true</code>
          </div>
        </div>

        <div class="row">
          <label class="col-sm-2 col-form-label">Number</label>
          <div class="col-sm-7">
            <ValidationProvider
              name="number"
              rules="required|numeric"
              v-slot="{ passed, failed }"
            >
              <fg-input type="text"
                        :error="failed ? 'The Number field is required': null"
                        :hasSuccess="passed"
                        name="number"
                        v-model="number">
              </fg-input>
            </ValidationProvider>
          </div>
          <div class="col-sm-3 label-on-right">
            <code>number:true</code>
          </div>
        </div>


        <div class="row">
          <label class="col-sm-2 col-form-label">Url</label>
          <div class="col-sm-7">
            <ValidationProvider
              name="url"
              :rules="{
                required: true,
                regex: /(https?:\/\/(?:[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?\.)+[a-z0-9][a-z0-9-]{0,61}[a-z0-9])(:?\d*)\/?([a-z_\/0-9\-#.]*)\??([a-z_\/0-9\-#=&]*)/g
              }"
              v-slot="{ passed, failed }"
            >
              <fg-input type="text"
                        :error="failed ? 'The Url field is required': null"
                        :hasSuccess="passed"
                        name="url"
                        v-model="url">
              </fg-input>
            </ValidationProvider>
          </div>
          <div class="col-sm-3 label-on-right">
            <code>url:true</code>
          </div>
        </div>

        <div class="row">
          <label class="col-sm-2 col-form-label">Equal to</label>
          <div class="col-sm-3">
            <ValidationProvider
              name="equal"
              rules="confirmed:confirmation"
              v-slot="{ passed, failed }"
            >
              <fg-input type="text"
                        :error="failed ? 'The idDestination confirmation does not match': null"
                        :hasSuccess="passed"
                        name="idSource"
                        placeholder="#idSource"
                        v-model="idSource">
              </fg-input>
            </ValidationProvider>
          </div>
          <div class="col-sm-3">
            <ValidationProvider
              name="equalTo"
              vid="confirmation"
              v-slot="{ passed, failed }"
            >
              <fg-input type="text"
                        name="idDestination"
                        placeholder="#idDestination"
                        v-model="idDestination">
              </fg-input>
            </ValidationProvider>
          </div>
          <div class="col-sm-3 label-on-right">
            <code>confirmed: 'idSource'</code>
          </div>
        </div>

        <div slot="footer" class="text-center">
          <p-button type="info" native-type="submit">Validate inputs</p-button>
        </div>

      </card>
    </form>
  </ValidationObserver>
</template>
<script>
  import { Card } from 'src/components/UIComponents'
  import { extend } from "vee-validate";
  import { required, numeric, regex, confirmed } from "vee-validate/dist/rules";

  extend("required", required);
  extend("numeric", numeric);
  extend("regex", regex);
  extend("confirmed", confirmed);

  export default {
    components: {
      Card
    },
    data() {
      return {
        required: "",
        email: "",
        number: "",
        url: "",
        idSource: "",
        idDestination: ""
      };
    },
    methods: {
      submit() {
        alert("Form has been submitted!");
      }
    }
  }
</script>
<style>
</style>
