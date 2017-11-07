# 1
增加imgfacade的相关接口
// 未来mv的相关都与mv开头
img表改名为mv_img
album表改名为mv_album
album_tag表改名为mv_album_tag

mv_img表中的album_id改为mv_album_id
增加img表

# 2
comment share/praise 初始值设置为0
event 增加share/praise字段
增加collection表
family表中 改head_img_id,cover_img_id
增加family_member表
整体把mv和dz从event中剥离出来
fetch把mv的内容值插入到mv_*的表中，不插入到event中
获取mv和dz的时候就是查询相关的mv*和dz*的表


#3 2017.11.6
dz_comment add column named is_hot,praise,share
update table dz change column source source_id int
change dz_comment content 2048
dz_comment delete deleted int

