package bani.lux.banikzn.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CreateHtml {
    public CreateHtml(String hiddenDays, String name) {
        String htmlCode = "<!DOCTYPE>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <title>Parcel Sandbox</title>\n" +
                "  <meta charset=\"UTF-8\" />\n" +
                "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.9.0/fullcalendar.min.css\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"float: left; width: 600px\" id=\"app\"></div>\n" +
                "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js\"></script>\n" +
                "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
                "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.9.0/fullcalendar.min.js\"></script>\n" +
                "<script>\n" +
                "  $(document).ready(function () {\n" +
                "    var today = new Date();\n" +
                "    var dd = today.getDate();\n" +
                "    var mm = today.getMonth() + 1;\n" +
                "    var yyyy = today.getFullYear();\n" +
                "    if (dd < 10) { dd = '0' + dd; }\n" +
                "    if (mm < 10) { mm = '0' + mm; }\n" +
                "    today = yyyy + '-' + mm + '-' + dd;\n" +
                "\n" +
                "    var data = [\n" +
                "              { \"title\": \"запривачено\", \"url\": \"https://google.com/\", \"start\": \"2023-05-13 21:00\", \"end\": \"2023-05-13 22:00\" },\n" +
                "              { \"id\": 999, \"title\": \"Repeating Event\", \"start\": \"2015-02-09T16:00:00\" }\n" +
                "            ]\n" +
                "    ;\n" +
                "\n" +
                "    $(\"#app\").fullCalendar({\n" +
                "      timeFormat: 'HH:mm',\n" +
                "      header: {\n" +
                "        left: \"prev,next today\",\n" +
                "        center: \"title\",\n" +
                "        right: \"agendaWeek,agendaDay\"\n" +
                "      },\n" +
                "      firstDay:1,\n" +
                "      slotLabelFormat:\"HH:mm\",\n" +
                "\n" +
                "      views: {\n" +
                "        businessWeek: {\n" +
                "          type: \"agendaWeek\",\n" +
                "          title: \"Apertura\",\n" +
                "          columnFormat: \"dddd\",\n" +
                "          slotDuration:'00:15:00',\n" +
                "          weekday: 'long',\n" +
                "          hiddenDays: ["+hiddenDays+"],\n" +
                "        }\n" +
                "      },\n" +
                "      defaultView: \"businessWeek\",\n" +
                "      allDaySlot: false,\n" +
                "      slotEventOverlap: false,\n" +
                "      agendaEventMinHeight: 40,\n" +
                "      columnHeaderFormat: \"ddd\",\n" +
                "      defaultDate: today,\n" +
                "      selectable: false,\n" +
                "      selectHelper: true,\n" +
                "      editable: false,\n" +
                "      eventLimit: true,\n" +
                "      events: data\n" +
                "    });\n" +
                "  });\n" +
                "</script>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        FileWriter fileWriter;
        {
            try {
                fileWriter = new FileWriter(new File("src/main/resources/templates",name+".html"));
                fileWriter.write(htmlCode);
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}