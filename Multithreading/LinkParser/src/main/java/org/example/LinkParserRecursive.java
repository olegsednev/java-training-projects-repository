package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkParserRecursive extends RecursiveAction {

    private String websiteAddress;
    private static final Pattern pattern = Pattern.compile("^https://lenta\\.ru/[^#\\.]+[^#]$");
    private static Set<String> linkSet = ConcurrentHashMap.newKeySet();

    public LinkParserRecursive(String websiteAddress) {
        this.websiteAddress = websiteAddress;
    }

    public static Set<String> getLinkSet() {
        return linkSet;
    }

    @Override
    protected void compute() {
        try {
            Thread.sleep(0);
            Document document = Jsoup.connect(websiteAddress).maxBodySize(0).get();
            Elements elements = document.select("a");
            List<LinkParserRecursive> taskList = new ArrayList<>();
            elements.forEach(element -> {
                        String absHref = element.attr("abs:href");
                        Matcher matcher = pattern.matcher(absHref);
                        if (matcher.find() && !linkSet.contains(absHref)) {
                            linkSet.add(absHref);
                            System.out.println(absHref);
                            LinkParserRecursive action = new LinkParserRecursive(absHref);
                            action.fork();
                            taskList.add(action);
                        }
                    }
            );
            for (LinkParserRecursive task : taskList) {
                task.join();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
