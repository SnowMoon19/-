<template>
  <div>
    <div class="header">
      <el-form :inline="true" :model="searchInline" class="demo-form-inline search">
        <el-form-item label="年份">
          <el-date-picker v-model="searchInline.year" type="year" placeholder="选择年"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getRecordListByQuery()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="body">
      <div class="record">
        <div class="tittle">
          <div class="text">Commemorate</div>
          <el-button class="button" icon="el-icon-plus" @click="addDialogVisible=true"/>
        </div>
        <el-timeline style="width: 90%">
          <el-timeline-item v-for="record in recordList" v-if="record.type!=='6'" :key="record.recordId" placement="top">
            <div class="timeline_tittle">
              <div class="text">{{ formatDate(record.date) }}</div>
              <el-link v-if="record.id.toString()===id" class="el-icon-delete button" @click="visible=true; deleteRecord=record"/>
              <el-link v-else class="el-icon-delete button" disabled/>
            </div>
            <el-card>
              <p v-if="record.type==='1'" style="font-size: 30px; color: crimson; font-weight: bold">{{ record.content }}</p>
              <p v-else-if="record.type==='2'" style="font-size: 28px; color: #ff7e00">{{ record.content }}</p>
              <p v-else-if="record.type==='3'" style="font-size: 26px; color: green">{{ record.content }}</p>
              <p v-else-if="record.type==='4'" style="font-size: 22px; color: deepskyblue">{{ record.content }}</p>
              <p v-else-if="record.type==='5'" style="font-size: 18px;">{{ record.content }}</p>
              <footer v-if="record.id.toString()===id" style="float: right; font-size: 18px; margin-bottom: 5px">{{ name }}</footer>
              <footer v-else-if="record.id.toString()===anotherId" style="float: right; font-size: 18px; margin-bottom: 5px">{{ anotherName }}</footer>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
      <div class="pity">
        <div class="tittle">
          <div class="text">Pity</div>
        </div>
        <el-timeline style="width: 90%">
          <el-timeline-item v-for="record in recordList" v-if="record.type==='6'" :key="record.recordId" placement="top">
            <div class="timeline_tittle">
              <div class="text">{{ formatDate(record.date) }}</div>
              <el-link v-if="record.id.toString()===id" class="el-icon-delete button" @click="visible=true; deleteRecord=record"/>
              <el-link v-else class="el-icon-delete button" disabled/>
            </div>
            <el-card>
              <p style="font-size: 18px; color: rgba(58,58,58,0.71)">{{ record.content }}</p>
              <footer v-if="record.id.toString()===id" style="float: right; font-size: 18px; color: rgba(58,58,58,0.71); margin-bottom: 5px">{{ name }}</footer>
              <footer v-else-if="record.id.toString()===anotherId" style="float: right; font-size: 18px; color: rgba(58,58,58,0.71); margin-bottom: 5px">{{ anotherName }}</footer>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
      <el-dialog :visible.sync="addDialogVisible" :modal="false" title="记录" width="800px">
        <el-form>
          <el-form-item label="日期" label-width="15%">
            <el-date-picker v-model="addForm.date" type="date" placeholder="Pick" style="width: 95%;"/>
          </el-form-item>
          <el-form-item label="内容" label-width="15%">
            <el-input v-model="addForm.content" type="textarea" style="width: 95%;"/>
          </el-form-item>
          <el-form-item label="等级" label-width="15%">
            <el-select v-model="addForm.type">
              <el-option label="daily" value="5"/>
              <el-option label="meaningful" value="4"/>
              <el-option label="important" value="3"/>
              <el-option label="pretty important" value="2"/>
              <el-option label="milestone" value="1"/>
              <el-option label="pity" value="6"/>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button @click="addDialogVisible=false;">取 消</el-button>
          <el-button type="primary" @click="addDialogVisible=false;addRecord()">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="visible" :modal="false" width="250px">
        <h3 style="text-align: center">确定要删除吗？</h3>
        <div slot="footer">
          <el-button @click="visible=false;">取消</el-button>
          <el-button type="primary" @click="visible=false;deleteRecordItem()">确定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>

import { getAnotherId, getAnotherUsername, getId, getName } from '../../utils/auth'
import { formatDay, getNowFormatDay } from '../../utils/dateTime'
import {getRecordList, getRecordListByQuery, updateRecord} from '../../api/module/memory/record'

export default {
  data() {
    return {
      id: getId(),
      anotherId: getAnotherId(),
      name: getName(),
      anotherName: getAnotherUsername(),
      searchInline: {
        year: ''
      },
      recordList: [],
      addDialogVisible: false,
      addForm: {
        date: getNowFormatDay(),
        content: '',
        type: 'daily'
      },
      visible: false,
      deleteRecord: {}
    }
  },
  created() {
    this.getRecordList()
  },
  methods: {
    getRecordList() {
      getRecordList(this.id, this.anotherId).then(response => {
        this.recordList = response.data
      }).catch(error => {
        console.log(error)
      })
    },
    formatDate(date) {
      return formatDay(date)
    },
    deleteRecordItem() {
      console.log(11)
      this.deleteRecord.isDeleted = 1
      updateRecord(this.deleteRecord).then(response => {
        this.getRecordList() // 重新获取列表
      }).catch(error => {
        console.log(error)
      })
    },
    addRecord() {
      const record = this.addForm
      record.id = this.id
      record.tag = null
      record.isDeleted = 0
      record.recordId = null
      record.date = getNowFormatDay(new Date(record.date))
      console.log(record.date)
      if (record.type === 'daily') record.type = '5'
      updateRecord(record).then(response => {
        this.addForm.date = getNowFormatDay()
        this.addForm.content = ''
        this.addForm.type = 'daily'
        this.getRecordList() // 重新获取列表
      }).catch(error => {
        console.log(error)
      })
    },
    /**
     * 条件查询
     */
    getRecordListByQuery() {
      // 转换查询年份的格式
      this.searchInline.year = new Date(this.searchInline.year).getFullYear()
      console.log(this.searchInline.year)
      if (isNaN(this.searchInline.year) || this.searchInline.year === 1970) {
        // todo 这里重新获取全部记录有点麻烦
        this.searchInline.year = NaN
        this.getRecordList()
      } else {
        getRecordListByQuery(this.id, this.anotherId, this.searchInline).then(response => {
          this.recordList = response.data
          this.searchInline.year = this.searchInline.year.toString() // 查完再转回来
        }).catch(error => {
          console.log(error)
        })
      }
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
/**
  设置滚动条样式
 */
::-webkit-scrollbar {display:none} // 隐藏滚动条
.header {
  height: 80px;
  .search {
    background: rgba(180,180,180,0.36);
    display: inline-block;
    padding-top: 20px;
    padding-left: 60px;
    margin-bottom: 20px;
    width: 100%;
  }
}

.year_title {
  height: 80px;
  width: 100%;
  text-align: center;
  margin-top: 25px;
  .tittle-text {
    font-family: "Arial";
    color: #000000;
    font-size: 50px;
    margin: auto;
  }
}

.record {
  margin: 2% 3% 0 3%;
  height: 750px;
  background: #52aeff;
  width: 40%;
  float: left;
  overflow-y: auto;
  border-radius: 25px;
  box-shadow: 0 2px 2px rgba(0, 0, 0, .12), 0 0 4px rgba(0, 0, 0, 0.8)
}
.pity {
  margin: 2% 3% 0 3%;
  height: 750px;
  background: rgba(201,195,199,0.68);
  width: 40%;
  float: left;
  overflow-y: auto;
  border-radius: 25px;
  box-shadow: 0 2px 2px rgba(0, 0, 0, .12), 0 0 4px rgba(0, 0, 0, 0.8)
}

.tittle {
  width: 100%;
  height: 50px;
  .text {
    font-size: 25px;
    font-weight: bold;
    margin-left: 2%;
    margin-top: 2%;
    float: left;
  }
  .button {
    display: inline-block;
    width: 8%;
    margin-left: 2%;
    margin-top: 1%;
  }
}
.timeline_tittle {
  width: 80%;
  height: 50px;
  .text {
    font-size: 18px;
    font-weight: bold;
    margin-left: 2%;
    margin-top: 4%;
    float: left;
  }
  .button {
    font-size: 15px;
    margin-left: 3%;
    margin-top: 4.5%;
  }
}
</style>
