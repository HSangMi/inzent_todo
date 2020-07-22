<template>
  <v-dialog v-model="openModal" max-width="800" persistent>
    <v-card>
      <v-form ref="form" v-model="valid" @submit.prevent="onSubmit" lazy-validation>
        <v-card-title class="headline grey lighten-2" primary-title>
          PROJECT INFO
          <v-spacer></v-spacer>
          <v-btn color="grey darken-1" icon @click="onClose">
            <v-icon color="grey darken-1">mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-card-text class="pb-0">
          <!-- <v-container> -->
          <v-row>
            <v-col cols="8">
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    label="PROJECT TITLE"
                    v-model="project.title"
                    :rules="titleRules"
                    :readonly="project.memberNo!==project.manager"
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="PROJECT DESCRIPTION" v-model="project.description"></v-text-field>
                </v-col>
                <v-col cols="12">
                  PRIVATE
                  <v-radio-group
                    :value="(project.usePublic ? 'true':'falses')"
                    required
                    :readonly="project.memberNo!==project.manager"
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
                <v-col cols="12">
                  <p>DUERATION</p>
                  <v-menu
                    v-model="startDatePicker"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    :disabled="project.memberNo!==project.manager"
                    transition="scale-transition"
                    offset-y
                    min-width="290px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="project.startDate"
                        label="Start date"
                        prepend-icon="mdi-calendar"
                        readonly
                        clearable
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker v-model="startDate" no-title @input="startDatePicker = false"></v-date-picker>
                  </v-menu>
                  <!-- </v-col> -->
                  <!-- <v-col cols="12" sm="6"> -->
                  <!-- <p>DUERATION</p> -->
                  <v-menu
                    v-model="endDatePicker"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    :disabled="project.memberNo!==project.manager"
                    transition="scale-transition"
                    offset-y
                    min-width="290px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="project.endDate"
                        label="End date"
                        prepend-icon="mdi-calendar"
                        readonly
                        clearable
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker v-model="endDate" no-title @input="endDatePicker = false"></v-date-picker>
                  </v-menu>
                </v-col>
                <v-col cols="12" sm="6">
                  PROJECT COVER
                  <v-radio-group v-model="coverColor" row :disabled="isCoverImg">
                    <v-radio
                      on-icon="mdi-check-circle"
                      off-icon="mdi-checkbox-blank-circle"
                      color="#EF9A9A"
                      value="EF9A9A"
                      class="red-icon"
                    ></v-radio>
                    <v-radio
                      on-icon="mdi-check-circle"
                      off-icon="mdi-checkbox-blank-circle"
                      color="#FFCC80"
                      value="FFCC80"
                      class="yellow-icon"
                    ></v-radio>
                    <v-radio
                      on-icon="mdi-check-circle"
                      off-icon="mdi-checkbox-blank-circle"
                      color="#81C784"
                      value="81C784"
                      class="green-icon"
                    ></v-radio>
                    <v-radio
                      on-icon="mdi-check-circle"
                      off-icon="mdi-checkbox-blank-circle"
                      color="#448AFF"
                      value="448AFF"
                      class="blue-icon"
                    ></v-radio>
                    <v-radio
                      on-icon="mdi-check-circle"
                      off-icon="mdi-checkbox-blank-circle"
                      color="#5C6BC0"
                      value="5C6BC0"
                      class="puple-icon"
                    ></v-radio>
                    <v-radio
                      on-icon="mdi-check-circle"
                      off-icon="mdi-checkbox-blank-circle"
                      color="#546E7A"
                      value="546E7A"
                      class="grey-icon"
                    ></v-radio>
                  </v-radio-group>
                </v-col>
                <v-col cols="12" sm="6">
                  <br />
                  <v-file-input
                    :rules="imgRules"
                    v-model="coverImgFile"
                    accept="image/png, image/jpeg, image/bmp"
                    placeholder="cover Image"
                    prepend-icon="mdi-camera"
                    @change="createBase64Image(coverImgFile)"
                  ></v-file-input>
                </v-col>
              </v-row>
            </v-col>
            <v-col cols="4">
              <p>
                MEMBERS
                <span class="pl-4">{{memberList.length}}명</span>
              </p>
              <div>
                <v-btn
                  color="rgb(88, 110, 150)"
                  flat
                  dark
                  block
                  depressed
                  @click.prevent="isOpenSearch = true"
                >
                  <v-icon>mdi-account-plus</v-icon>
                </v-btn>
                <user-search
                  :openModal="isOpenSearch"
                  @close="isOpenSearch = false"
                  @addMember="addMember"
                />
                <div
                  v-if="memberList!==[]"
                  max-height="100%"
                  style="height:550px"
                  class="overflow-y-auto mt-3"
                >
                  <v-list>
                    <v-list-item v-for="mem in memberList" :key="mem.memberNo">
                      <v-list-item-avatar>
                        <v-avatar v-if="mem.imgCode">
                          <img :src="mem.imgCode" />
                        </v-avatar>
                        <v-avatar v-else color="grey">
                          <v-icon fab dark>mdi-account</v-icon>
                        </v-avatar>
                        <!-- <v-avatar color="grey" size="56" class="white--text">{{ mem.name }}</v-avatar> -->
                      </v-list-item-avatar>
                      <v-list-item-content>
                        <v-list-item-title>{{ mem.userId }}</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-list>
                  <!-- <v-tooltip v-for="member in members" :key="member.id" bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-avatar v-if="member.imgCode" size="56" class="user-avatars">
                        <img :src="member.imgCode" v-bind="attrs" v-on="on" />
                      </v-avatar>
                      <v-avatar v-else size="56" class="user-avatars" color="grey">
                        <v-icon fab dark v-bind="attrs" v-on="on">mdi-account</v-icon>
                      </v-avatar>
                    </template>
                    <span>{{ member.name }}</span>
                  </v-tooltip>-->
                </div>
              </div>
            </v-col>
          </v-row>
          <!-- </v-container> -->
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="onClose">Cancle</v-btn>
          <v-btn color="blue darken-1" text type="submit">SAVE</v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapActions, mapState } from "vuex";
import UserSearch from "../user/UserSearch.vue";

export default {
  props: ["openModal"],
  components: {
    UserSearch
  },
  data: () => ({
    isOpenSearch: false,
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
    coverImgFile: undefined,
    imgRules: [
      value =>
        !value ||
        value.size < 2000000 ||
        "Avatar size should be less than 2 MB!"
    ],
    titleRules: [
      v => !!v || "title is required",
      v => (v && v.length <= 100) || "title must be less than 100 characters"
    ],
    privateRules: [v => !!v || "private is required"],
    valid: true,
    members: []
  }),
  methods: {
    ...mapActions(["ADD_PROJECT"]),
    onSubmit() {
      // Create Project
      console.log("UPDATE Project");
      console.log(this.project);
      // if (this.validate()) {
      //   let formData = new FormData();
      //   formData.append("title", this.title);
      //   formData.append("description", this.description);
      //   formData.append("usePublic", this.usePublic);
      //   formData.append("startDate", this.startDate);
      //   formData.append("endDate", this.endDate);
      //   if (this.coverImg !== undefined) {
      //     console.log("test------------");
      //     console.log(this.coverImg);
      //     formData.append("coverImg", this.coverImg);
      //   }
      //   formData.append("coverColor", this.coverColor);
      //   var mems = [];
      //   for (var a in this.members) {
      //     if (this.userInfo.id !== this.members[a].id)
      //       mems.push(this.members[a].id);
      //   }
      //   formData.append("members", mems);

      //   this.ADD_PROJECT(formData).then(data => {
      //     // console.log("-----");
      //     // console.log(data);
      //     this.$router.push(`/projects/${data.id}`);
      //   });
      //   this.formClear();
      //   this.$emit("close");
      // }
    },
    onClose() {
      // this.openModal = false;
      // this.formClear();
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
    addMember(members) {
      this.members = members;
    },
    createBase64Image(fileObject) {
      console.log("file object", fileObject);
      if (fileObject !== undefined) {
        const reader = new FileReader();
        reader.onload = e => {
          this.image = e.target.result;
          console.log("image", this.image);
          this.coverImg = this.image;
        };
        reader.readAsDataURL(fileObject);
      } else {
        this.coverImg = undefined;
      }
    }
    // getImgCode(item) {
    //   return "data:image;base64," + item.imgCode;
    // }
  },
  computed: {
    ...mapState({
      userInfo: "userInfo",
      project: "project",
      memberList: "memberList"
      // user 멤버 no가져오기..
    }),
    isCoverImg() {
      return this.coverImgFile === undefined ? false : true;
    }
  }
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
.red-icon .v-icon {
  color: #ef9a9a;
}
.yellow-icon .v-icon {
  color: #ffcc80;
}
.green-icon .v-icon {
  color: #81c784;
}
.blue-icon .v-icon {
  color: #448aff;
}
.puple-icon .v-icon {
  color: #5c6bc0;
}
.grey-icon .v-icon {
  color: #546e7a;
}
.user-avatars {
  margin-left: -15px;
}
</style>
