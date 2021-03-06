package org.cientopolis.samplers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class SamplersClassGenerator {

    private String package_name;
    private String googleMaps_API_KEY;
    private List<StepClassGenerator> steps;

    public SamplersClassGenerator(String package_name, String manifest_path, String strings_path) {
        this.package_name = package_name;
        this.steps = new ArrayList<>();
        XMLManagement.setManifestFileName(manifest_path+"AndroidManifest.xml");
        XMLManagement.setStringsFileName(strings_path+"strings.xml");
    }


    public void setGoogleMaps_API_KEY(String googleMaps_API_KEY) {
        this.googleMaps_API_KEY = googleMaps_API_KEY;

        XMLManagement.addGoogleMapsAPIKey(this.googleMaps_API_KEY);
    }

    public void addStep(StepClassGenerator step) {
        steps.add(step);
    }

    public void generateMainActivity(String path, String activity_name, String welcomeMessage, String net_config_url, String net_config_paramName) {

        List<String> output = new ArrayList<>();

        output.add("package " + package_name+";");
        output.add("");
        output.add("import android.os.Bundle;");
        output.add("import android.content.Intent;");
        output.add("import java.util.ArrayList;");
        output.add("import org.cientopolis.samplers.ui.SamplersMainActivity;");
        output.add("import org.cientopolis.samplers.network.NetworkConfiguration;");
        output.add("import org.cientopolis.samplers.model.*;");
        output.add("");
        output.add("public class "+activity_name+" extends SamplersMainActivity {");
        output.add("");
        output.add("    @Override");
        output.add("    protected void onCreate(Bundle savedInstanceState) {");
        output.add("        super.onCreate(savedInstanceState);");

        // Set Network Configuration
        output.add("        NetworkConfiguration.setURL(\"" + net_config_url + "\");");
        output.add("        NetworkConfiguration.setPARAM_NAME(\"" + net_config_paramName + "\");");

        // Set Welcome Message
        String varName = "welcomeMessage";
        XMLManagement.addString(varName, welcomeMessage);

        output.add("        String "+varName +" = getResources().getString(R.string."+varName+"); ");
        output.add("        lb_main_welcome_message.setText("+varName+");");

        output.add("    }");
        output.add("");

        output.add("    @Override");
        output.add("    protected Workflow getWorkflow() {");
        output.add("        Workflow workflow = new Workflow();");

        for (int i = 0; i < steps.size(); i++) {
            output.addAll(steps.get(i).generateStep(i,"workflow"));
        }

        output.add("        return workflow;");
        output.add("    }");


        output.add("}");

        saveFile(output, path+activity_name+".java");

        // TODO Parametrize Label and Theme
        XMLManagement.addMainActivity(activity_name,"@string/app_name", "@style/AppTheme");

    }

    private void saveFile(List<String> output, String filename) {

        try {
            File outFile = new File(filename);

            System.out.println(outFile.getCanonicalPath());

            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

            for (String line: output) {
                writer.write(line+"\n");
            }

            writer.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
