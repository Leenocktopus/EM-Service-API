package leandoer.example.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import leandoer.example.entity.Order;
import leandoer.example.entity.OrderProduct;

import java.io.IOException;

public class OrderSerializer extends JsonSerializer<Order> {
    @Override
    public void serialize(Order order, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", order.getOrderId());
        jsonGenerator.writeStringField("name", order.getCustomerName());
        jsonGenerator.writeStringField("phone", order.getCustomerPhone());
        jsonGenerator.writeStringField("email", order.getCustomerEmail());
        jsonGenerator.writeStringField("date", order.getDate().toString());
        jsonGenerator.writeStringField("status", order.getOrderStatus().toString());
        jsonGenerator.writeArrayFieldStart("products");
        for (OrderProduct op : order.getProducts()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", op.getProduct().getProductId());
            jsonGenerator.writeStringField("name", op.getProduct().getName());
            jsonGenerator.writeNumberField("stock", op.getProduct().getAmountInStock());
            jsonGenerator.writeNumberField("price", op.getProduct().getPrice());
            jsonGenerator.writeNumberField("quantity", op.getQuantity());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
