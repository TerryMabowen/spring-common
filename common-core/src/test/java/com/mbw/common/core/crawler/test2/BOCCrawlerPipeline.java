package com.mbw.common.core.crawler.test2;

import com.mbw.common.core.crawler.boc.BOCCrawlerData;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-03 14:21
 */
public class BOCCrawlerPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if ("tds".equals(entry.getKey())) {
                BOCCrawlerData value = (BOCCrawlerData)entry.getValue();
                System.out.println(value.toString());
            }
        }
    }
}
