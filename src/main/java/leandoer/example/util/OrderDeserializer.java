package leandoer.example.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import leandoer.example.entity.Order;
import leandoer.example.entity.Product;

import java.io.IOException;
import java.util.Date;

public class OrderDeserializer extends JsonDeserializer<Order> {
    @Override
    public Order deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Order order = new Order();
        order.setCustomerName(jsonNode.get("name").asText());
        order.setCustomerEmail(jsonNode.get("email").asText());
        order.setCustomerPhone(jsonNode.get("phone").asText());
        order.setDate(objectMapper.convertValue(jsonNode.get("date"), Date.class));

        JsonNode productsArray = jsonNode.get("products");
        for (JsonNode node : productsArray) {
            Product p = new Product();
            p.setProductId(node.get("id").asText());
            order.addProduct(p, node.get("quantity").asInt());
        }

        return order;
    }
}
