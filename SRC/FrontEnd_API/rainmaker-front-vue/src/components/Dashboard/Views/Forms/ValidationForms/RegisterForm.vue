<template>
  <ValidationObserver v-slot="{ handleSubmit }">
    <form @submit.prevent="handleSubmit(submit)">
      <div class="card">
        <div class="card-header">
          <h4 class="card-title">
            Register Form
          </h4>
        </div>
        <div class="card-body">
          <div class="form-group">
            <label>Email address</label>
            <ValidationProvider
              name="email"
              rules="required|email"
              v-slot="{ passed, failed }"
            >
              <fg-input  type="email"
                         :error="failed ? 'The Email field is required': null"
                         :hasSuccess="passed"
                         name="email"
                         v-model="email">
              </fg-input>
            </ValidationProvider>
          </div>
          <div class="form-group">
            <label>Password</label>
            <ValidationProvider
              vid="confirmation"
              rules="required"
              v-slot="{ passed, failed }"
            >
              <fg-input  type="password"
                         :error="failed ? 'The Password field is required': null"
                         :hasSuccess="passed"
                         name="password"
                         v-model="password">
              </fg-input>
            </ValidationProvider>
          </div>
          <div class="form-group">
            <label>Confirm Password</label>
            <ValidationProvider
              rules="required|confirmed:confirmation"
              v-slot="{ passed, failed }"
            >
              <fg-input  type="password"
                         :error="failed ? 'The Confirm field is required': null"
                         :hasSuccess="passed"
                         name="confirm"
                         v-model="confirmPassword">
              </fg-input>
            </ValidationProvider>
          </div>
        </div>
        <div class="card-footer text-right">
          <p-checkbox class="pull-left">Subscribe to newsletter</p-checkbox>
          <p-button type="info" native-type="submit">Register</p-button>
        </div>
      </div>
    </form>
  </ValidationObserver>
</template>
<script>
import { extend } from "vee-validate";
import { required, email, confirmed } from "vee-validate/dist/rules";

extend("email", email);
extend("required", required);
extend("confirmed", confirmed);

export default {
  data() {
    return {
      email: "",
      password: "",
      confirmPassword: ""
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
