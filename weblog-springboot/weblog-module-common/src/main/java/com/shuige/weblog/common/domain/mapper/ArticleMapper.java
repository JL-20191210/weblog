package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuige.weblog.common.domain.dos.ArticleDO;
import com.shuige.weblog.common.domain.dos.ArticlePublishCountDO;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author felix
 * @date 2024/7/9 10:52
 */
public interface ArticleMapper extends BaseMapper<ArticleDO> {

    /**
     * 查询文章分页数据
     * @param current 当前页
     * @param size 每页多少数据
     * @param title 文章标题
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    default Page<ArticleDO> selectPageList(Long current, Long size, String title, LocalDate startDate,LocalDate endDate){
        // 分页对象(查询第几页、每页多少数据)
        Page<ArticleDO> page = new Page<>(current,size);

        // 构建查询条件
        LambdaQueryWrapper<ArticleDO> wrapper = Wrappers.<ArticleDO>lambdaQuery()
                .like(StringUtils.isNotBlank(title),ArticleDO::getTitle,title)
                .ge(Objects.nonNull(startDate),ArticleDO::getCreateTime,startDate)
                .le(Objects.nonNull(endDate),ArticleDO::getCreateTime,endDate)
                .orderByDesc(ArticleDO::getWeight) // 按权重倒序
                .orderByDesc(ArticleDO::getCreateTime);

        return selectPage(page,wrapper);
    }

    /**
     * 根据文章 ID 批量查询
     * @param current
     * @param size
     * @param articleIds
     * @return
     */
    default Page<ArticleDO> selectPageListByArticleIds(Long current, Long size, List<Long> articleIds) {
        // 分页对象(查询第几页、每页多少数据)
        Page<ArticleDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<ArticleDO> wrapper = Wrappers.<ArticleDO>lambdaQuery()
                .in(ArticleDO::getId, articleIds) // 批量查询
                .orderByDesc(ArticleDO::getCreateTime); // 按创建时间倒叙

        return selectPage(page, wrapper);
    }

    /**
     * 查询上一篇文章
     * @param articleId
     * @return
     */
    default ArticleDO selectPreArticle(Long articleId){
        return selectOne(Wrappers.<ArticleDO>lambdaQuery()
                .orderByAsc(ArticleDO::getId)
                .gt(ArticleDO::getId,articleId)
                .last("limit 1"));
    }

    /**
     * 查询下一篇文章
     * @param articleId
     * @return
     */
    default ArticleDO selectNextArticle(Long articleId){
        return selectOne(Wrappers.<ArticleDO>lambdaQuery()
                .orderByDesc(ArticleDO::getId)
                .lt(ArticleDO::getId,articleId)
                .last("limit 1"));
    }

    /**
     * 阅读量+1
     * @param articleId
     * @return
     */
    default int increaseReadNum(Long articleId){
        // 执行 SQL : UPDATE t_article SET read_num = read_num + 1 WHERE (id = XX)
        return  update(null,Wrappers.<ArticleDO>lambdaUpdate()
                .setSql("read_num = read_num+1 ")
                .eq(ArticleDO::getId,articleId));
    }

    /**
     * 查询所有记录的阅读量
     * @return
     */
    default List<ArticleDO> selectAllReadNum(){
        // 设置仅查询 read_num 字段
        return selectList(Wrappers.<ArticleDO>lambdaQuery()
                .select(ArticleDO::getReadNum));
    }

    @Select("SELECT DATE(create_time) AS date, COUNT(*) AS count FROM t_article \n" +
            "WHERE create_time >= #{startDate} AND create_time < #{endDate} \n" +
            "GROUP BY DATE(create_time) \n")
    List<ArticlePublishCountDO> selectDateArticlePublishCount(LocalDate startDate,LocalDate endDate);

    /**
     * 查询最大权重值记录
     * @return
     */
    default ArticleDO selectMaxWeight() {
        return selectOne(Wrappers.<ArticleDO>lambdaQuery()
                .orderByDesc(ArticleDO::getWeight) // 按权重值降序排列
                .last("LIMIT 1")); // 仅查询出一条
    }

    /**
     * 批量更新文章
     * @param articleDO
     * @param ids
     * @return
     */
    default int updateByIds(ArticleDO articleDO, List<Long> ids) {
        return update(articleDO, Wrappers.<ArticleDO>lambdaUpdate()
                .in(ArticleDO::getId, ids));
    }
}
