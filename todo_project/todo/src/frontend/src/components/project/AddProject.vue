<template>
  <v-dialog v-model="openModal" max-width="640" persistent>
    <v-card>
      <v-form
        ref="form"
        v-model="valid"
        @submit.prevent="onSubmit"
        lazy-validation
      >
        <v-card-title class="headline grey lighten-2" primary-title>
          CREATE PROJECT
          <v-spacer></v-spacer>
        </v-card-title>
        <v-card-text>
          <!-- <v-container> -->
          <v-row>
            <v-col cols="12">
              <v-text-field
                label="PROJECT TITLE*"
                v-model="title"
                :rules="titleRules"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field
                label="PROJECT DESCRIPTION"
                v-model="description"
                hint="explain your project"
              ></v-text-field>
            </v-col>
            <v-col cols="12">
              PRIVATE*
              <v-radio-group
                v-model="usePublic"
                required
                row
                :rules="privateRules"
              >
                <br />
                <v-radio label="Public" value="true"></v-radio>
                <v-spacer></v-spacer>
                <v-radio label="Private" value="false"></v-radio>
                <v-spacer></v-spacer>
              </v-radio-group>
            </v-col>
            <v-col cols="12" sm="6">
              DUERATION
              <v-menu
                v-model="startDatePicker"
                :close-on-content-click="false"
                :nudge-right="40"
                transition="scale-transition"
                offset-y
                min-width="290px"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="startDate"
                    label="Start date"
                    prepend-icon="mdi-calendar"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="startDate"
                  no-title
                  @input="startDatePicker = false"
                ></v-date-picker>
              </v-menu>
            </v-col>
            <v-col cols="12" sm="6">
              <br />
              <v-menu
                v-model="endDatePicker"
                :close-on-content-click="false"
                :nudge-right="40"
                transition="scale-transition"
                offset-y
                min-width="290px"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="endDate"
                    label="End date"
                    prepend-icon="mdi-calendar"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="endDate"
                  no-title
                  @input="endDatePicker = false"
                ></v-date-picker>
              </v-menu>
            </v-col>
            <v-col cols="12" sm="6">
              PROJECT COVER
              <v-radio-group v-model="coverColor" row :disabled="isCoverImg">
                <v-radio
                  on-icon="mdi-check-circle"
                  off-icon="mdi-checkbox-blank-circle"
                  color="primary"
                  value="1976d2"
                  class="primary-icon"
                ></v-radio>
                <v-radio
                  on-icon="mdi-check-circle"
                  off-icon="mdi-checkbox-blank-circle"
                  color="secondary"
                  value="424242"
                  class="secondary-icon"
                ></v-radio>
                <v-radio
                  on-icon="mdi-check-circle"
                  off-icon="mdi-checkbox-blank-circle"
                  color="success"
                  value="4caf50"
                  class="success-icon"
                ></v-radio>
                <v-radio
                  on-icon="mdi-check-circle"
                  off-icon="mdi-checkbox-blank-circle"
                  color="info"
                  value="2196f3"
                  class="info-icon"
                ></v-radio>
                <v-radio
                  on-icon="mdi-check-circle"
                  off-icon="mdi-checkbox-blank-circle"
                  color="warning"
                  value="fb8c00"
                  class="warning-icon"
                ></v-radio>
                <v-radio
                  on-icon="mdi-check-circle"
                  off-icon="mdi-checkbox-blank-circle"
                  color="error"
                  value="ff5252"
                  class="error-icon"
                ></v-radio>
              </v-radio-group>
            </v-col>
            <v-col cols="12" sm="6">
              <br />
              <v-file-input
                :rules="imgRules"
                v-model="coverImg"
                accept="image/png, image/jpeg, image/bmp"
                placeholder="cover Image"
                prepend-icon="mdi-camera"
              ></v-file-input>
            </v-col>
            <v-col cols="12">
              MEMBERS
              <br />
              <v-autocomplete
                v-model="members"
                :items="people"
                chips
                label="Select"
                item-text="name"
                item-value="name"
                multiple
              >
                <template v-slot:selection="data">
                  <v-chip
                    v-bind="data.attrs"
                    :input-value="data.selected"
                    close
                    @click="data.select"
                    @click:close="remove(data.item)"
                  >
                    <v-avatar left>
                      <v-img :src="data.item.avatar"></v-img>
                    </v-avatar>
                    {{ data.item.name }}
                  </v-chip>
                </template>
                <template v-slot:item="data">
                  <template v-if="typeof data.item !== 'object'">
                    <v-list-item-content
                      v-text="data.item"
                    ></v-list-item-content>
                  </template>
                  <template v-else>
                    <v-list-item-avatar>
                      <img :src="data.item.avatar" />
                    </v-list-item-avatar>
                    <v-list-item-content>
                      <v-list-item-title
                        v-html="data.item.name"
                      ></v-list-item-title>
                      <v-list-item-subtitle
                        v-html="data.item.group"
                      ></v-list-item-subtitle>
                    </v-list-item-content>
                  </template>
                </template>
              </v-autocomplete>
            </v-col>
          </v-row>
          <!-- </v-container> -->
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="onClose">Cancle</v-btn>
          <v-btn color="blue darken-1" text type="submit">Create</v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapActions } from "vuex";

export default {
  props: ["openModal"],
  data: () => ({
    dialog: false,
    title: "",
    description: "",
    usePublic: undefined,
    startDate: "", //new Date().toISOString().substr(0, 10),
    endDate: "",
    startDatePicker: false,
    endDatePicker: false,
    coverColor: "1976d2",
    coverImg: undefined,
    imgRules: [
      (value) =>
        !value ||
        value.size < 2000000 ||
        "Avatar size should be less than 2 MB!",
    ],
    titleRules: [
      (v) => !!v || "title is required",
      (v) => (v && v.length <= 100) || "title must be less than 100 characters",
    ],
    privateRules: [(v) => !!v || "private is required"],
    valid: true,
    people: [
      { header: "Group 1" },
      {
        name: "Sandra Adams",
        group: "Group 1",
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      },
      {
        name: "Ali Connors",
        group: "Group 1",
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      },
      {
        name: "Trevor Hansen",
        group: "Group 1",
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      },
      {
        name: "Tucker Smith",
        group: "Group 1",
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      },
      { divider: true },
      { header: "Group 2" },
      {
        name: "Britta Holt",
        group: "Group 2",
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      },
      {
        name: "Jane Smith ",
        group: "Group 2",
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      },
      {
        name: "John Smith",
        group: "Group 2",
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      },
      {
        name: "Sandra Williams",
        group: "Group 2",
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      },
    ],
    members: [],
  }),
  methods: {
    ...mapActions(["ADD_PROJECT"]),
    onSubmit() {
      // Create Project
      console.log("create Project");
      if (this.validate()) {
        let formData = new FormData();
        formData.append("title", this.title);
        formData.append("description", this.description);
        formData.append("usePublic", this.usePublic);
        formData.append("startDate", this.startDate);
        formData.append("endDate", this.endDate);
        if (this.coverImg !== undefined) {
          console.log("test------------");
          console.log(this.coverImg);
          formData.append("coverImg", this.coverImg);
        }
        formData.append("coverColor", this.coverColor);
        formData.append("members", this.members);

        this.ADD_PROJECT(formData).then((data) => {
          console.log("-----");
          console.log(data);
          this.$router.push(`/projects/${data.id}`);
        });
        this.formClear();
        this.$emit("close");
      }
    },
    onClose() {
      // this.openModal = false;
      this.formClear();
      this.$emit("close");
    },
    remove(item) {
      const index = this.members.indexOf(item.name);
      if (index >= 0) this.members.splice(index, 1);
    },
    formClear() {
      this.$refs.form.reset();
      this.coverColor = "1976d2";
    },
    validate() {
      return this.$refs.form.validate();
    },
  },
  computed: {
    isCoverImg() {
      return this.coverImg === undefined ? false : true;
    },
  },
};
</script>

<style>
.v-input--radio-group.v-input--radio-group--row .v-radio {
  margin-right: 0px;
}
.v-radio .v-icon {
  margin: 0px;
  font-size: 32px;
}
.primary-icon .v-icon {
  color: #1976d2;
}
.secondary-icon .v-icon {
  color: #424242;
}
.success-icon .v-icon {
  color: #4caf50;
}
.info-icon .v-icon {
  color: #2196f3;
}
.warning-icon .v-icon {
  color: #fb8c00;
}
.error-icon .v-icon {
  color: #ff5252;
}
</style>
