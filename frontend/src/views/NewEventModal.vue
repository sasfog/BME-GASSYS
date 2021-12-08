<template>
  <div class="modal show" tabindex="-1" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header d-flex">
          <div class="d-flex justify-content-center w-100">
            <h1>Új esemény</h1>
          </div>
          <button
            type="button"
            class="btn-close"
            v-on:click="closeModal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body d-flex flex-column justify-content-center">
          <div class="d-flex">
            <label for="eventName">Név:</label>
            <input
              class="ms-5 w-100"
              v-model="eventName"
              placeholder="Specify event name.."
              name="eventName"
            />
          </div>
          <label class="my-1" for="description">Leírás:</label>
          <textarea
            v-model="description"
            placeholder="Add a description.."
            name="description"
          />
          <div class="d-flex justify-content-center my-1">
            <h3>Kezdés</h3>
          </div>
          <div
            id="kezdes-blokk"
            class="d-flex flex-row justify-content-between w-100"
          >
            <div class="d-flex flex-column w-50 pe-2">
              <div class="d-flex justify-content-between my-2">
                <label for="date">Dátum:</label>
                <input
                  class="ms-3 w-60"
                  type="date"
                  name="date"
                  v-model="startDate"
                />
              </div>
              <div class="d-flex justify-content-between my-2">
                <label for="capacity">Férőhely:</label>
                <input
                  class="ms-3 w-60"
                  type="number"
                  name="capacity"
                  min="0"
                  v-model="capacity"
                />
              </div>
            </div>
            <div class="d-flex flex-column w-50">
              <div class="d-flex justify-content-between my-2 ps-3">
                <label for="start-time">Időpont:</label>
                <input
                  class="ms-3"
                  type="time"
                  name="start-time"
                  v-model="startTime"
                />
              </div>
              <div class="d-flex justify-content-between my-2 ps-3">
                <label for="duration">Időtartam:</label>
                <input
                  class="ms-3"
                  type="time"
                  name="duration"
                  v-model="duration"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="d-flex flex-row align-items-center">
          <div class="d-flex justify-content-between ps-3 w-50 pe-2">
            <label for="category">Kategória:</label>
            <select
              class="ms-3 w-60"
              name="category"
              v-model="selectedCategory"
            >
              <option
                v-for="category in categories"
                v-bind:key="category"
                v-bind:value="category"
              >
                {{ category }}
              </option>
            </select>
          </div>
          <div class="ps-3 w-50 d-flex justify-content-between my-2 ps-3 pe-3">
            <label for="colour">Szín:</label>
            <input
              class="ms-3"
              name="colour"
              type="color"
              v-model="selectedColour"
            />
          </div>
        </div>
        <div class="d-flex flex-wrap w-100 my-3 justify-content-center">
          <categoryItem
            v-for="category in selectedCategories"
            v-bind:listItem="category"
            v-bind:key="category"
            v-bind:value="category"
            v-on:deleteCategory="deleteCategoryFromList"
          ></categoryItem>
        </div>
        <div class="modal-footer d-flex justify-content-center">
          <button class="btn btn-primary" v-on:click="addEvent">
            Új esemény
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import categoryItem from "./CategoryItem.vue";
import axios from "axios";
import * as config from "../scripts/constants.js";
export default {
  components: {
    categoryItem,
  },
  props: ["text", "imageSource"],
  methods: {
    closeModal() {
      this.$emit("close-modal");
    },
    deleteCategoryFromList(categoryItem) {
      this.selectedCategories.delete(categoryItem);
    },
    addEvent() {
      var startDateTime = new Date(
        this.startDate + " " + this.startTime
      ).toJSON();
      var durationArray = this.duration.split(":");
      var formattedDuration = durationArray[0] * 60 + durationArray[1];
      console.log(this.selectedColour);
      var postData = {
        organiserId: 1,
        name: this.eventName,
        startDateTime: startDateTime,
        duration: formattedDuration,
        color: this.selectedColour,
        capacity: this.capacity,
        description: this.description,
      };

      axios
        .post(config.eventEndpoint, postData)
        .then((response) => {
          console.log(response);
          this.closeModal();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  data() {
    return {
      categories: [
        "category1",
        "category2",
        "category3",
        "category4",
        "category5",
      ],
      selectedCategory: null,
      selectedColour: "#000000",
      selectedCategories: new Set(),
      eventName: "",
      duration: 0,
      startDate: "",
      startTime: "",
      capacity: 0,
      description: "",
    };
  },
  watch: {
    selectedColour: function (val) {
      console.log(val);
    },
    selectedCategory: function (val) {
      console.log(val);
      this.selectedCategories.add(val);
    },
  },
};
</script>

<style>
#kezdes-blokk {
  border-top: 1px solid black;
  border-bottom: 1px solid black;
}

.w-60 {
  width: 60%;
}
</style>
