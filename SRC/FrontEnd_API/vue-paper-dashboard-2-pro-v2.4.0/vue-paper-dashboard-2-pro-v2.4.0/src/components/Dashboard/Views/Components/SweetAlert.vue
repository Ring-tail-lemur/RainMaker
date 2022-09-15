<template>
  <div class="container-fluid">
    <h4 class="card-title">Sweet Alert 2</h4>
    <p class="category">A beautiful plugin, that replace the classic alert, Handcrafted by our friend <a target="_blank"
                                                                                                         rel="noopener"
                                                                                                         href="https://twitter.com/t4t5">Tristan Edwards</a>. Please check out the <a
      href="http://limonte.github.io/sweetalert2/" target="_blank" rel="noopener">full documentation.</a></p>

    <br><br>

    <div class="places-sweet-alerts">
      <div class="row">
        <div class="col-md-3">
          <card card-body-classes="text-center">
              <p class="card-text">Basic example</p>
              <p-button type="default" round outline @click="showSwal('basic')">Try me!</p-button>
          </card>
        </div>
        <div class="col-md-3">
          <card card-body-classes="text-center">
              <p class="card-text">A title with a text under</p>
              <p-button type="default" round outline @click="showSwal('title-and-text')">Try me!</p-button>
          </card>
        </div>
        <div class="col-md-3">
          <card card-body-classes="text-center">
              <p class="card-text">A success message</p>
              <p-button type="default" round outline @click="showSwal('success-message')">Try me!</p-button>
          </card>
        </div>
        <div class="col-md-3">
          <card card-body-classes="text-center">
              <p class="card-text">Custom HTML description</p>
              <p-button type="default" round outline @click="showSwal('custom-html')">Try me!</p-button>
          </card>
        </div>
        <div class="col-md-3">
          <card card-body-classes="text-center">
              <p class="card-text">A warning message, with a function attached to the "Confirm" Button...</p>
              <p-button type="default" round outline @click="showSwal('warning-message-and-confirmation')">
                Try me!
              </p-button>
          </card>
        </div>
        <div class="col-md-3">
          <card card-body-classes="text-center">
              <p class="card-text">...and by passing a parameter, you can execute something else for "Cancel"</p>
              <p-button type="default" round outline @click="showSwal('warning-message-and-cancel')">Try me!</p-button>
          </card>
        </div>
        <div class="col-md-3">
          <card card-body-classes="text-center">
              <p class="card-text">A message with auto close timer set to 2 seconds</p>
              <p-button type="default" round outline @click="showSwal('auto-close')">Try me!</p-button>
          </card>
        </div>
        <div class="col-md-3">
          <card card-body-classes="text-center">
              <p class="card-text">Modal window with input field</p>
              <p-button type="default" round outline @click="showSwal('input-field')">Try me!</p-button>
          </card>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import Swal from 'sweetalert2';
  import {Button, Card} from 'src/components/UIComponents';
  export default {
    components: {
      [Button.name]: Button,
      Card
    },
    methods: {
      showSwal (type) {
        if (type === "basic") {
        const swalWithBootstrapButtons = Swal.mixin({
          customClass: {
            confirmButton: "btn btn-success",
          },
          buttonsStyling: false,
        });
        swalWithBootstrapButtons.fire({
          title: `Here's a message!`,
        });
      } else if (type === "title-and-text") {
        const swalWithBootstrapButtons2 = Swal.mixin({
          customClass: {
            confirmButton: "btn btn-info",
          },
          buttonsStyling: false,
        });
        swalWithBootstrapButtons2.fire({
          title: `Here's a message!`,
          text: `It's pretty, isn't it?`,
        });
      } else if (type === "success-message") {
        const swalWithBootstrapButtons3 = Swal.mixin({
          customClass: {
            confirmButton: "btn btn-success",
          },
          buttonsStyling: false,
        });
        swalWithBootstrapButtons3.fire({
          title: `Good job!`,
          text: "You clicked the button!",
        });
      } else if (type === "warning-message-and-confirmation") {
        const swalWithBootstrapButtons4 = Swal.mixin({
          customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger",
          },
          buttonsStyling: false,
        });
        swalWithBootstrapButtons4
          .fire({
            title: "Are you sure?",
            text: `You won't be able to revert this!`,
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "Cancel",
            showCancelButton: true,
          })
          .then((result) => {
            if (result.value) {
              const swalWithBootstrapButtons5 = Swal.mixin({
                customClass: {
                  confirmButton: "btn btn-success",
                },
                buttonsStyling: false,
              });
              swalWithBootstrapButtons5.fire({
                title: "Deleted!",
                text: "Your file has been deleted.",
              });
            }
          });
      } else if (type === "warning-message-and-cancel") {
        const swalWithBootstrapButtons6 = Swal.mixin({
          customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger",
          },
          buttonsStyling: false,
        });
        swalWithBootstrapButtons6
          .fire({
            title: "Are you sure?",
            text: "You will not be able to recover this imaginary file!",
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, keep it",
            showCancelButton: true,
          })
          .then((result) => {
            if (result.value) {
              const swalWithBootstrapButtons6 = Swal.mixin({
                customClass: {
                  confirmButton: "btn btn-success",
                },
                buttonsStyling: false,
              });
              swalWithBootstrapButtons6.fire({
                title: "Deleted!",
                text: "Your imaginary file has been deleted.",
              });
            } else if (result.dismiss === Swal.DismissReason.cancel) {
              const swalWithBootstrapButtons7 = Swal.mixin({
                customClass: {
                  confirmButton: "btn btn-info",
                },
                buttonsStyling: false,
              });
              swalWithBootstrapButtons7.fire({
                title: "Cancelled",
                text: "Your imaginary file is safe :)",
              });
            }
          });
      } else if (type === "custom-html") {
        const swalWithBootstrapButtons8 = Swal.mixin({
          customClass: {
            confirmButton: "btn btn-success",
          },
          buttonsStyling: false,
        });
        swalWithBootstrapButtons8.fire({
          title: "HTML example",
          html: `You can use <b>bold text</b>,
            <a href="http://github.com">links</a>
            and other HTML tags`,
        });
      } else if (type === "auto-close") {
        Swal.fire({
          title: "Auto close alert!",
          text: "I will close in 2 seconds.",
          timer: 2000,
          showConfirmButton: false,
        });
      } else if (type === "input-field") {
        const swalWithBootstrapButtons9 = Swal.mixin({
          customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger",
          },
          buttonsStyling: false,
        });
        swalWithBootstrapButtons9
          .fire({
            title: "Input something",
            html: `<div class="form-group">
            <input id="input-field" type="text" class="form-control" />
            </div>`,
            showCancelButton: true,
          })
          .then(() => {
            const swalWithBootstrapButtons10 = Swal.mixin({
              customClass: {
                confirmButton: "btn btn-success",
              },
              buttonsStyling: false,
            });
            swalWithBootstrapButtons10.fire({
              html:
                "You entered: <strong>" +
                document.getElementById("input-field").value +
                "</strong>",
            });
          });
        }
      }
    }
  }
</script>
<style>
</style>
