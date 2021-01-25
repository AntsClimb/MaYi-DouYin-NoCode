// pages/md5/md5.js
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    dataUrl:'',
    showFlag: false,
    dataPath: ''
  },

  postUpload() {
    var _this = this;
    wx.chooseVideo({
      success (res) {
        wx.showLoading({
          title: '正在上传视频'
        });
        const tempFilePath = res.tempFilePath
        wx.uploadFile({
          url: app.globalData.default+"/upload", //仅为示例，非真实的接口地址
          filePath: tempFilePath,
          name: 'file',
          success:res=>{
            var data = JSON.parse(res.data);
            console.log(data);
            if(data.code==200){
              _this.setData({
                dataUrl: data.data.video_url,
                dataPath: data.data.video_path,
                showFlag: true
              })
              wx.hideLoading();
            } else {
              wx.hideLoading();
            }
          }
        })
      },
      fail(res){
        wx.showToast({
          title: '上传失败',
        })
      }
    })
  },

  clearMd5() {
    var a = this;
    wx.showLoading({
      title: '正在去除视频md5',
    })
    wx.request({
      url: app.globalData.default+"/md5",
      data: {
        dataPath: a.data.dataPath
      },
      success: function (res) {
        if(res.data.code==200){
          wx.downloadFile({
            url: res.data.data,
            success: function (o) {
                wx.hideLoading(), 
                wx.saveVideoToPhotosAlbum({
                    filePath: o.tempFilePath,
                    success: function (o) {
                        wx.showToast({
                          title: '保存成功',
                          icon: 'success'
                        })
                        setTimeout(function () {
                            wx.setClipboardData({
                              data: '',
                            })
                        }, 1e3)
                    },
                    fail: function (o) {
                      wx.showToast({
                        title: '保存失败',
                        icon: 'none'
                      })
                    }
                })
                a.setData({
                  dataUrl: '',
                  dataPath: '',
                  showFlag: false
                })
            },
            fail: function (o) {
                n = null, wx.hideLoading(), t.showToast('下载失败')
            }
        })
        }
      },
      fail: function (res) {
        wx.hideLoading();
        wx.showToast({
          title: '去除视频md5失败',
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})