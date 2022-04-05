package com.kdt.progmrs.kdt;


import com.kdt.progmrs.kdt.Config.AppConfiguration;
import com.kdt.progmrs.kdt.order.OrderItem;
import com.kdt.progmrs.kdt.order.OrderProperties;
import com.kdt.progmrs.kdt.order.OrderService;
import com.kdt.progmrs.kdt.voucher.FixedAmountVoucher;
import com.kdt.progmrs.kdt.voucher.VoucherRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class OrderTester {
    public static void main(String[] args) throws IOException {

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        applicationContext.register(AppConfiguration.class);
//        ConfigurableEnvironment environment = applicationContext.getEnvironment();
//        environment.setActiveProfiles("local");
//        applicationContext.refresh();

        Resource resource = applicationContext.getResource("application.yaml");

//        Resource resource = applicationContext.getResource("file:sample.txt");
        System.out.println("resource = " + resource.getClass().getCanonicalName());
        File file = resource.getFile();
        List<String> strings = Files.readAllLines(file.toPath());
        System.out.println("strings = " + strings.stream().reduce("", (a, b) -> a + "\n" + b));

        Resource resource2 = applicationContext.getResource("https://stackoverflow.com/");
        ReadableByteChannel readableByteChannel = Channels.newChannel(resource2.getURL().openStream());
        String collect = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        System.out.println("collect = " + collect);

        /**
         * Environment 활용하기
         * AppConfig에 @PropertySource("application.properties") 등록 후 끌어옮
         */

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String version = environment.getProperty("kdt.version");
        System.out.println("version = " + version);
        Integer property = environment.getProperty("kdt.minimum-order-amount", Integer.class);
        System.out.println("property = " + property);
        List property1 = environment.getProperty("kdt.support-vendors", List.class);
        System.out.println("support-vendors = " + property1);
        List property2 = environment.getProperty("kdt.description", List.class);
        System.out.println("property2 = " + property2);

        var customerId = UUID.randomUUID();

        OrderProperties bean = applicationContext.getBean(OrderProperties.class);
        String version1 = bean.getVersion();
        System.out.println("version1[forBean] = " + version1);
        int minimumOrderAmount = bean.getMinimumOrderAmount();
        System.out.println("minimumOrderAmount[forBean] = " + minimumOrderAmount);
        List<String> supportVendors = bean.getSupportVendors();
        System.out.println("supportVendors[forBean] = " + supportVendors);
        String description = bean.getDescription();
        System.out.println("description[forBean] = " + description);

//        var voucherRepository = applicationContext.getBean(VoucherRepository.class);


        VoucherRepository voucherRepository = applicationContext.getBean(VoucherRepository.class);
//        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
        System.out.println(MessageFormat.format("voucherRepository = {0}", voucherRepository));
//        var voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
//        System.out.println(MessageFormat.format("voucherRepository2 = {0}", voucherRepository2));

//        System.out.println(MessageFormat.format("voucherRepository == voucherRepository2 -> {0}", voucherRepository == voucherRepository2));

        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        var orderService = applicationContext.getBean(OrderService.class);

        var orderItems = new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};

        var order = orderService.createOrder(customerId, orderItems, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAMount {0} is not 90L", order.totalAmount()));


        applicationContext.close();

    }
}
