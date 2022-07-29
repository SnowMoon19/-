<template>
  <div>
    <div class="header">
      <el-form :inline="true" :model="searchInline" class="demo-form-inline search">
        <el-form-item label="年份">
          <el-date-picker v-model="searchInline.year" type="year" placeholder="选择年"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getPhotographListByQuery">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="body">
      <el-card class="all-photos">
        <!--每一年的相册-->
        <el-card v-for="photo in photos" :key="photo.index" class="photo-of-year">
          <div class="photograph_name">{{ getPhotographName(photo) }}</div>
          <div class="show-task">
            <el-button v-for="ph in photo" :key="ph.index" class="task_type_btn">
              <div :style="randomRgb()" class="photo_date">
                {{ formatDate(ph[0].date) }}
              </div>
              <el-carousel height="205px" trigger="click" indicator-position="none">
                <el-carousel-item v-for="p in ph" :key="p.index" :interval="20000">
                  <img :src="p.url" style="margin-right: 7%" @click="infoPhoto=ph;infoDialogVisible=true;" @load="checkPics">
                </el-carousel-item>
              </el-carousel>
            </el-button>
          </div>
        </el-card>
      </el-card>
      <el-button class="add-photo" @click="addDialogVisible=true"><i class="el-icon-plus"/></el-button>
      <!--嵌套表单照片添加-->
      <el-dialog :visible.sync="addDialogVisible" :modal="false" title="上传图片" width="800px">
        <el-form>
          <el-form-item label="选择时间" label-width="15%">
            <el-date-picker v-model="uploadForm.uploadDate" type="date" placeholder="选择日期" style="width: 95%;"/>
          </el-form-item>
          <el-form-item label="选择图片" label-width="15%">
            <el-upload
              ref="upload"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :on-change="updateUploadPhotoList"
              :auto-upload="false"
              :multiple="true"
              :limit="3"
              accept=".jpg,.png"
              action="#"
              list-type="picture-card">
              <i class="el-icon-plus"/>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible" :modal="false">
              <img :src="dialogImageUrl" width="100%" alt="">
            </el-dialog>
          </el-form-item>
          <el-form-item label="添加描述" label-width="15%">
            <el-input v-model="uploadForm.uploadComment" type="textarea" style="width: 95%;"/>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button @click="addDialogVisible=false;">取 消</el-button>
          <el-button type="primary" @click="uploadPhotos">确 定</el-button>
        </div>
      </el-dialog>
      <!--照片显示-->
      <el-dialog :visible.sync="infoDialogVisible" :modal="false" :title="infoPhoto[0].date" width="900px" append-to-body>
        <el-form v-if="infoDialogVisible">
          <el-form-item v-if="infoDialogVisible" label="上传" label-width="13%">
            <div v-if="infoPhoto[0].creator.toString()===id" style="width: 90%;">{{ name }}</div>
            <div v-else style="width: 90%;">{{ anotherName }}</div>
          </el-form-item>
          <el-form-item v-if="infoDialogVisible" label="描述" label-width="13%">
            <el-input v-model="infoPhoto[0].comment" type="textarea" disabled style="width: 95%;"/>
          </el-form-item>
          <div v-if="infoDialogVisible" class="show-pics">
            <el-carousel :autoplay="false" height="500px" trigger="click" indicator-position="none">
              <el-carousel-item v-for="p in infoPhoto" :key="p.index" style="height: 400px; width: 710px;">
                <img :src="p.url" style="cursor: pointer" @click="showPicUrl=p.url;showPicDialogVisible=true" @load="showPics">
              </el-carousel-item>
            </el-carousel>
          </div>
        </el-form>
        <div slot="footer">
          <el-button @click="infoDialogVisible=false">取 消</el-button>
          <el-button type="primary" @click="infoDialogVisible=false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="showPicDialogVisible" :modal="false" width="1120px">
        <img v-if="showPicDialogVisible" :src="showPicUrl" @load="originalPic">
      </el-dialog>
    </div>
  </div>
</template>

<script>
import      { getAnotherId, getAnotherUsername, getId, getName } from '../../utils/auth'
import { formatDateWithoutMonthAndYear, getNowFormatDay } from '../../utils/dateTime'
import axios from 'axios'
import { getPhotographListByQuery, getPhotoIdList, getPhotoList } from '../../api/module/memory/photo'

export default {
  data() {
    return {
      id: getId(),
      anotherId: getAnotherId(),
      name: getName(),
      anotherName: getAnotherUsername(),
      addDialogVisible: false, // 添加弹窗
      photographIdList: [], // 相册id，更新相册的时候要用到
      photos: [], // 对应相册id的照片
      photoUrl: 'http://localhost:8000/', // 文件服务器的地址
      uploadForm: {
        uploadDate: getNowFormatDay(), // 上传照片的时间默认今天
        uploadComment: '',
        uploadPhotoList: [] // 上传照片列表
      },
      infoDialogVisible: false, // 信息弹窗
      showPicDialogVisible: false, // 显示图片的弹窗
      showPicUrl: '', // 显示图片的url
      infoPhoto: [{
        photographId: 0,
        photoId: 0,
        url: '',
        date: '',
        creator: 0,
        comment: ''
      }],
      dialogImageUrl: '',
      dialogVisible: false,
      searchInline: {
        year: ''
      },
      // 随机色彩列表
      randomRgb(item) {
        const R = Math.floor(Math.random() * 130 + 20)
        const G = Math.floor(Math.random() * 130 + 20)
        const B = Math.floor(Math.random() * 130 + 20)
        return {
          background: 'rgb(' + R + ',' + G + ',' + B + ', .5)'
        }
      }
    }
  },
  created() {
    this.getPhotographId(this.id, this.anotherId) // 共享相册
  },
  methods: {
    checkPics(e) {
      if (e.target.width > e.target.height) {
        e.target.style.width = '355px'
        e.target.style.height = '200px'
      } else {
        e.target.style.width = '180px'
        e.target.style.height = '250px'
      }
    },
    showPics(e) {
      if (e.target.width > e.target.height) {
        e.target.style.width = '710px'
        e.target.style.height = '400px'
      } else {
        e.target.style.width = '360px'
        e.target.style.height = '500px'
        e.target.style.marginLeft = '25%'
      }
    },
    originalPic(e) {
      if (e.target.width > e.target.height) {
        // 横图设置宽为1080
        const ratio = e.target.width / 1080 // 原图宽度与1080的比值
        const height = e.target.height / ratio
        e.target.style.width = '1080px'
        e.target.style.height = height + 'px'
      } else {
        // 竖图设置宽为608
        const ratio = e.target.width / 608 // 原图宽度与1080的比值
        const height = e.target.height / ratio
        e.target.style.width = '1080px'
        e.target.style.height = height + 'px'
        e.target.style.width = '608px'
        e.target.style.height = height + 'px'
        e.target.style.marginLeft = '236px'
      }
    },
    /**
     * 获取相册列表
     */
    getPhotographId(id, anotherId) {
      getPhotoIdList(id, anotherId).then(response => {
        this.photographIdList = response.data
        for (let i = 0; i < this.photographIdList.length; ++i) {
          this.photos.push([])
        }
        for (let i = 0; i < this.photographIdList.length; ++i) {
          this.getPhotos(this.photographIdList[i].photographId, i)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    getPhotos(photographId, i) {
      getPhotoList(photographId).then(response => {
        // 每一个相册的数据再按照时间归为一类
        const data = response.data
        let index = 0
        if (data.length > 0) {
          // console.log(this.photos)
          // console.log(i)
          this.photos[i].push([]) // 添加日期数组
          // 相册里有超出一个照片才显示
          data[0].url = this.photoUrl + data[0].url // 服务器url + 文件url等于访问路径
          this.photos[i][index].push(data[0])// 日期数组里添加照片
        }
        for (let j = 1; j < data.length; ++j) {
          data[j].url = this.photoUrl + data[j].url
          if (data[j].date === data[j - 1].date) {
            this.photos[i][index].push(data[j])
          } else {
            this.photos[i].push([])
            ++index
            this.photos[i][index].push(data[j])
          }
        }
      }).catch(error => {
        console.log(error)
      })
    },
    /**
     * 条件查询
     */
    getPhotographListByQuery() {
      // 转换查询年份的格式
      this.searchInline.year = new Date(this.searchInline.year).getFullYear()
      console.log(this.searchInline.year)
      this.photos.length = 0 // 初始化相册列表
      if (isNaN(this.searchInline.year) || this.searchInline.year === 1970) {
        this.searchInline.year = NaN
        this.getPhotographId(this.id, this.anotherId)
      } else {
        getPhotographListByQuery(this.id, this.anotherId, this.searchInline).then(response => {
          this.photographIdList = response.data
          for (let i = 0; i < this.photographIdList.length; ++i) {
            this.photos.push([])
          }
          for (let i = 0; i < this.photographIdList.length; ++i) {
            this.getPhotos(this.photographIdList[i].photographId, i)
          }
          this.searchInline.year = this.searchInline.year.toString() // 查完再转回来
        }).catch(error => {
          console.log(error)
        })
      }
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    /**
     *  在选择照片变化时更新上传列表
     */
    updateUploadPhotoList(file, fileList) {
      if (file.size / (1024 * 1024) > 10) { // 限制文件大小
        this.$message.warning(`当前限制文件大小不能大于10M`)
        return false
      }
      this.uploadForm.uploadPhotoList = fileList
    },
    /**
     * 上传照片列表
     */
    uploadPhotos() {
      if (this.uploadForm.uploadPhotoList.length > 0) {
        const formData = new FormData()
        this.uploadForm.uploadPhotoList.forEach(file => formData.append('file', file.raw))
        let photographId = '-1'
        // 看当前是否是在该年第一次上传图片，如果是的话photographId值为-1，让后端处理创建
        for (let i = 0; i < this.photographIdList.length; ++i) {
          if (this.photographIdList[i].name === new Date(this.uploadForm.uploadDate).getFullYear().toString()) {
            photographId = this.photographIdList[i].photographId
            break
          }
        }
        console.log(photographId)
        formData.append('photographId', photographId)
        this.uploadForm.uploadDate = getNowFormatDay(new Date(this.uploadForm.uploadDate)) // 上传的时间转换格式，因为后端是要就收string类型的数据
        formData.append('date', this.uploadForm.uploadDate)
        formData.append('creator', this.id)
        formData.append('comment', this.uploadForm.uploadComment)
        // 上传多张照片 todo 放到api里
        axios.post('http://localhost:8003/memory/photo', formData, { headers: { 'Content-Type': 'multipart/form-data' }}).then(response => {
          this.addDialogVisible = false
          this.$refs.upload.clearFiles() // 上传成功后把上传列表的图片删除
          this.uploadForm.uploadPhotoList = []
          this.photos.length = 0
          this.getPhotographId(this.id, this.anotherId) // 重新加载图片
        }).catch(error => {
          this.addDialogVisible = false
          console.log(error)
        })
      }
    },
    /**
     * 格式化日期
     */
    formatDate(date) {
      return formatDateWithoutMonthAndYear(date)
    },
    /**
     * 返回相册标题
     */
    getPhotographName(photo) {
      if (photo.length > 0) {
        const tmp = photo[0]
        if (tmp.length > 0) {
          return tmp[0].date[0] + '\n' + tmp[0].date[1] + '\n' + tmp[0].date[2] + '\n' + tmp[0].date[3]
        }
      }
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
/**
  设置滚动条样式
 */
::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 6px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 6px;
}
::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 6px;
  box-shadow: inset 0 0 5px rgba(97, 184, 179, 0.1);
  background: white;
}
::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  box-shadow: inset 0 0 5px rgba(87, 175, 187, 0.1);
  border-radius: 6px;
  //background: #a0e6e3;
}
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
.add-photo {
  width: 60%;
  margin-left: 20%;
  height: 50px;
  font-size: 30px;
  font-weight: bold;
  color: black;
  background: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
  border-radius: 15px;
  &:hover {
    background: rgba(230,187,217,0.95);
  }
}
.all-photos {
  margin: 30px 30px 0;
  height: 700px;
  background: rgba(221,255,250,0.95);
  border-radius: 25px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, 0.8);
  overflow-y: auto;
  .photo-of-year {
    height: 305px;
    background: rgba(255,240,245,0.95);
    border-radius: 25px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, 0.8);
    margin-bottom: 23px;
    .photograph_name {
      float: left;
      height: 270px;
      width: 3%;
      background: rgba(221,255,250,0.95);
      font-size: 38px;
      //padding-left: 18px;
      padding-top: 40px;
      text-align: center;
      border-radius: 25px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 4px rgba(0, 0, 0, 0.8);
    }
    .show-task {
      overflow-x: auto;
      white-space: nowrap;
      margin: auto;
      height: 280px;
      width: 92%;
      .task_type_btn {
        background: rgba(221,255,250,0.95);
        margin-right: 30px;
        border-radius: 20px;
        color: cornsilk;
        border-left-color: white;
        border-right-color: white;
        border-width: 5px;
        border-top-color: white;
        border-bottom-color: white;
        height: 265px;
        width: 380px;
        font-size: 30px;
        box-shadow: 0 0 5px rgba(255, 255, 255, 20);
        .photo_date {
          font-size: 18px;
          font-weight: bold;
          height: 20px;
        }
      }
    }
  }
}

.show-pics {
  background: black;
  height: 400px;
  width: 710px;
  margin: 50px auto;
}

/**
  设置走马灯的样式
 */
.el-carousel__item:nth-child(2n) {
  height: 200px;
  width: 355px;
  background-color: white;
}

.el-carousel__item:nth-child(2n+1) {
  height: 200px;
  width: 355px;
  background-color: white;
}

/**
  设置上传框的样式
 */
//.el-upload--picture-card {
//  width: 160px;
//  height: 90px;
//}
.el-upload {
  width: 200px;
  height: 150px;
}
.el-upload-list--picture-card .el-upload-list__item{
  width: 200px;
  height: 150px;
}
</style>
