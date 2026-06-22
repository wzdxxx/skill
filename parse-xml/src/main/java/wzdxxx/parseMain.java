package wzdxxx;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class parseMain {
    public static void main(String[] args) throws Exception {
        // 创建SAX解析器
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        // 创建XML处理器
        PersonHandler handler = new PersonHandler();

        // 获取XML文件输入流
        InputStream xmlInput = parseMain.class.getClassLoader().getResourceAsStream("parsed.xml");

        // 解析XML文件
        saxParser.parse(xmlInput, handler);

        // 输出解析结果
        List<Person> persons = handler.getPersons();
        for (Person person : persons) {
            log.info("person-{}", person);
        }
    }

    // 自定义SAX处理器
    private static class PersonHandler extends DefaultHandler {
        private List<Person> persons = new ArrayList<>();
        private Person currentPerson;
        private StringBuilder currentValue = new StringBuilder();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            // 当遇到person标签时，创建新的Person对象
            if ("person".equals(qName)) {
                currentPerson = new Person();
                currentPerson.setId(Integer.parseInt(attributes.getValue("id")));
            }
            // 重置当前值
            currentValue.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            currentValue.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            // 当遇到person标签结束，将当前Person对象添加到列表
            if ("person".equals(qName)) {
                persons.add(currentPerson);
                currentPerson = null;
            }

            // 设置Person对象的属性
            if (currentPerson != null) {
                switch (qName) {
                    case "name":
                        currentPerson.setName(currentValue.toString().trim());
                        break;
                    case "age":
                        currentPerson.setAge(Integer.parseInt(currentValue.toString().trim()));
                        break;
                    case "gender":
                        currentPerson.setGender(currentValue.toString().trim());
                        break;
                    case "email":
                        currentPerson.setEmail(currentValue.toString().trim());
                        break;
                }
            }
        }

        // 获取解析出的Person列表
        public List<Person> getPersons() {
            return persons;
        }
    }
}
