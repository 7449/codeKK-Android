package com.codekk.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codekk.R
import com.codekk.mvp.model.PactModel
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.setItemLayoutId
import com.xadapter.setOnBind
import com.xadapter.setOnItemClickListener
import com.xadapter.setText
import kotlinx.android.synthetic.main.activity_pact.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.browse
import java.util.*

/**
 * by y on 2017/5/26.
 */

class PactActivity : AppCompatActivity() {

    private lateinit var mAdapter: XAdapter<PactModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pact)
        toolbar.setTitle(R.string.pact_title)
        setSupportActionBar(toolbar)
        mAdapter = XAdapter()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

        val list = ArrayList<PactModel>()

        list.add(
                PactModel(getString(R.string.backlayout),
                        "Copyright 2013 Issac Wong\n\n" +
                                "   Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "   you may not use this file except in compliance with the License.\n" +
                                "   You may obtain a copy of the License at\n\n" +
                                "     http://www.apache.org/licenses/LICENSE-2.0\n\n" +
                                "   Unless required by applicable law or agreed to in writing, software\n" +
                                "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "   See the License for the specific language governing permissions and\n" +
                                "   limitations under the License.",
                        "https://github.com/ikew0ng/SwipeBackLayout"))

        list.add(
                PactModel(getString(R.string.flexbox),
                        "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "   you may not use this file except in compliance with the License.\n" +
                                "   You may obtain a copy of the License at\n\n" +
                                "       http://www.apache.org/licenses/LICENSE-2.0\n\n" +
                                "   Unless required by applicable law or agreed to in writing, software\n" +
                                "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "   See the License for the specific language governing permissions and\n" +
                                "   limitations under the License.",
                        "https://github.com/google/flexbox-layout"))

        list.add(
                PactModel(getString(R.string.leakcanary),
                        "Copyright 2015 Square, Inc.\n\n" +
                                "    Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "    you may not use this file except in compliance with the License.\n" +
                                "    You may obtain a copy of the License at\n\n" +
                                "       http://www.apache.org/licenses/LICENSE-2.0\n\n" +
                                "    Unless required by applicable law or agreed to in writing, software\n" +
                                "    distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "    See the License for the specific language governing permissions and\n" +
                                "    limitations under the License.",
                        "https://github.com/square/leakcanary"))
        list.add(
                PactModel(getString(R.string.materialdialog),
                        "The MIT License (MIT)\n\n" +
                                "Copyright (c) 2014-2016 Aidan Michael Follestad\n\n" +
                                "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                                "of this software and associated documentation files (the \"Software\"), to deal\n" +
                                "in the Software without restriction, including without limitation the rights\n" +
                                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                                "copies of the Software, and to permit persons to whom the Software is\n" +
                                "furnished to do so, subject to the following conditions:\n\n" +
                                "The above copyright notice and this permission notice shall be included in all\n" +
                                "copies or substantial portions of the Software.\n\n" +
                                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                                "SOFTWARE.\n",
                        "https://github.com/afollestad/material-dialogs"))
        list.add(
                PactModel(getString(R.string.markdownView),
                        "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "   you may not use this file except in compliance with the License.\n" +
                                "   You may obtain a copy of the License at\n\n" +
                                "       http://www.apache.org/licenses/LICENSE-2.0\n\n" +
                                "   Unless required by applicable law or agreed to in writing, software\n" +
                                "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "   See the License for the specific language governing permissions and\n" +
                                "   limitations under the License.",
                        "https://github.com/mukeshsolanki/MarkdownView-Android"))
        list.add(
                PactModel(getString(R.string.rxnetwork),
                        "Copyright (C) 2016 yuebigmeow@gamil.com\n\n" +
                                "    Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "    you may not use this file except in compliance with the License.\n" +
                                "    You may obtain a copy of the License at\n\n" +
                                "       http://www.apache.org/licenses/LICENSE-2.0\n\n" +
                                "    Unless required by applicable law or agreed to in writing, software\n" +
                                "    distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "    See the License for the specific language governing permissions and\n" +
                                "    limitations under the License.",
                        "https://github.com/7449/RxNetWork"))

        list.add(
                PactModel(getString(R.string.rxJava),
                        "Copyright (c) 2016-present, RxJava Contributors.\n\n" +
                                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "you may not use this file except in compliance with the License.\n" +
                                "You may obtain a copy of the License at\n\n" +
                                "<http://www.apache.org/licenses/LICENSE-2.0>\n\n" +
                                "Unless required by applicable law or agreed to in writing, software\n" +
                                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "See the License for the specific language governing permissions and\n" +
                                "limitations under the License.",
                        "https://github.com/ReactiveX/RxJava"))
        list.add(
                PactModel(getString(R.string.retrofit),
                        "Copyright 2013 Square, Inc.\n\n" +
                                "    Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "    you may not use this file except in compliance with the License.\n" +
                                "    You may obtain a copy of the License at\n\n" +
                                "       http://www.apache.org/licenses/LICENSE-2.0\n\n" +
                                "    Unless required by applicable law or agreed to in writing, software\n" +
                                "    distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "    See the License for the specific language governing permissions and\n" +
                                "    limitations under the License.",
                        "https://github.com/square/retrofit"))
        list.add(
                PactModel(getString(R.string.xadapter),
                        "Copyright (C) 2016 yuebigmeow@gamil.com\n\n" +
                                "    Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "    you may not use this file except in compliance with the License.\n" +
                                "    You may obtain a copy of the License at\n\n" +
                                "       http://www.apache.org/licenses/LICENSE-2.0\n\n" +
                                "    Unless required by applicable law or agreed to in writing, software\n" +
                                "    distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "    See the License for the specific language governing permissions and\n" +
                                "    limitations under the License.",
                        "https://github.com/7449/XAdapter"))
        recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_pact)
                .setOnItemClickListener { _, _, info -> browse(info.url, true) }
                .setOnBind { holder, _, entity -> onXBind(holder, entity) }
    }

    private fun onXBind(holder: XViewHolder, pactModel: PactModel) {
        holder.setText(R.id.tv_project_name, pactModel.projectName)
        holder.setText(R.id.tv_project_url, pactModel.license)
    }
}
