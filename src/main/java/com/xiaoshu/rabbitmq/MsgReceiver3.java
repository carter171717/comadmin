package com.xiaoshu.rabbitmq;

import com.xiaoshu.common.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_B)
@Slf4j
public class MsgReceiver3 {

    @RabbitHandler
    public void process(String message) {
       // String jsonString = new String(message.getBody());

         log.info("第三个消费者，接收处理队列A当中的消息： " + message);
    }

}