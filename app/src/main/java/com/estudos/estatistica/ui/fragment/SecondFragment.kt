package com.estudos.estatistica.ui.fragment

const val FREQUENCIA_DADOS_NAO_AGRUPADOS = 1
/*
class SecondFragment : Fragment() {


    private val viewModel: SecondFragmentViewModel by viewModel { parametersOf(args.type) }

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val adapter = DadosAdapter()

    lateinit var navController: NavController

    private var frequencia: Int? = null
    private var intervalo: Float? = null
    private var limiteInferior: Float? = null
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setType()
        addObservers()
        addListenners()
        configureClickAdd()
        binding.headerTitle.apply {
            showFac(false)
            showXi(false)
            showXifi(false)
        }
        binding.recyclerNumberList.adapter = adapter
    }

    private fun setType() {
        when (viewModel.typeOfCalc) {
            ActionHome.CONTINUOUS_DATA -> {
                binding.numberInit.text = "Li: "
                binding.numberInit.visibility = View.VISIBLE
                val dialog = DialogCustom("classes")
                dialog.isCancelable = false
                dialog.show(childFragmentManager, "DIALOG_CUSTOM")
                dialog.setOnClickListenner {
                    viewModel.qtdValores = it
                }
            }
            ActionHome.DISCRETE_DATA -> {
                binding.numberInit.text = "Variavel: "
                binding.numberInit.visibility = View.VISIBLE
                val dialog = DialogCustom("valores")
                dialog.isCancelable = false
                dialog.show(childFragmentManager, "DIALOG_CUSTOM")
                dialog.setOnClickListenner {
                    viewModel.qtdValores = it
                }
            }
            ActionHome.UNGROUPED_DISCRETE_DATA -> {
                binding.headerTitle.setTextClasses("Valores")
                val dialog = DialogCustom("valores")
                dialog.isCancelable = false
                dialog.show(childFragmentManager, "DIALOG_CUSTOM")
                dialog.setOnClickListenner {
                    viewModel.qtdValores = it
                }
            }
        }
    }


    private fun addObservers() {
        val dialogLoading = DialogLoading()
        viewModel.number.observe(viewLifecycleOwner) {
            it.validateViewToVisibleOrGone(binding.btnRemoveNumber)
            binding.numberResult.text = it
        }
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(requireContext(), binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                dialogLoading.isCancelable = false
                dialogLoading.show(childFragmentManager, "loadingCustom")
            } else {
                dialogLoading.dismiss()
            }
        }
        viewModel.calc.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.executaCalculos()
            }
        }
        viewModel.calculo.observe(viewLifecycleOwner) {
            navController.navigate(
                SecondFragmentDirections.actionSecondFragmentToNavGraphTable(it)
            )
        }
    }

    private fun addListenners() {
        binding.secondBtnBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.btn1.setOnClickListener {
            viewModel.addNumberToString(binding.btn1.text.toString())
        }
        binding.btn2.setOnClickListener {
            viewModel.addNumberToString(binding.btn2.text.toString())
        }
        binding.btn3.setOnClickListener {
            viewModel.addNumberToString(binding.btn3.text.toString())
        }
        binding.btn4.setOnClickListener {
            viewModel.addNumberToString(binding.btn4.text.toString())
        }
        binding.btn5.setOnClickListener {
            viewModel.addNumberToString(binding.btn5.text.toString())
        }
        binding.btn6.setOnClickListener {
            viewModel.addNumberToString(binding.btn6.text.toString())
        }
        binding.btn7.setOnClickListener {
            viewModel.addNumberToString(binding.btn7.text.toString())
        }
        binding.btn8.setOnClickListener {
            viewModel.addNumberToString(binding.btn8.text.toString())
        }
        binding.btn9.setOnClickListener {
            viewModel.addNumberToString(binding.btn9.text.toString())
        }
        binding.btn10.setOnClickListener {
            viewModel.addNumberToString(binding.btn10.text.toString())
        }
        binding.btn11.setOnClickListener {
            viewModel.addNumberToString(binding.btn11.text.toString())
        }
        binding.btnRemoveNumber.setOnClickListener {
            viewModel.removeLastCharacter()
        }
    }

    private fun configureClickAdd() {
        var contador = 0
        binding.btnAdd.setOnClickListener {
            if(binding.numberResult.text.toString().isBlankOrEmpty()){
                Snackbar.make(requireContext(), binding.root, "Informe um valor para adicionar a lista!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val numberFloat = binding.numberResult.text.toString().toFloat()
            when (viewModel.typeOfCalc) {
                ActionHome.CONTINUOUS_DATA -> {
                    if (contador == 0) {
                        if(limiteInferior == null){
                            limiteInferior = numberFloat
                            binding.numberInit.visibility = View.VISIBLE
                            binding.numberInit.text = ("Li: $limiteInferior")
                            binding.numberFim.visibility = View.VISIBLE
                            binding.numberFim.text = ("Intervalo: ")
                            contador++
                            zeraNumberResult()
                            return@setOnClickListener
                        }

                        addFrequencia(numberFloat)
                        configuraViewInitEFrequencia()

                        contador = 0
                        return@setOnClickListener
                    }
                    if (contador == 1) {
                        intervalo = numberFloat
                        binding.numberFim.text = ("Intervalo: $intervalo")
                        binding.numberFrequencia.visibility = View.VISIBLE
                        binding.numberFrequencia.text = ("Frequencia:")
                        zeraNumberResult()
                        contador++
                        return@setOnClickListener
                    }
                    if (contador == 2) {
                        addFrequencia(numberFloat)
                        contador = 0
                        configuraViewInitEFrequencia()
                    }
                }
                ActionHome.DISCRETE_DATA -> {
                    if (contador == 0) {
                        limiteInferior = numberFloat
                        binding.numberInit.visibility = View.VISIBLE
                        binding.numberInit.text = ("Variavel: $limiteInferior")
                        binding.numberFim.visibility = View.VISIBLE
                        binding.numberFim.text = "Frequencia: "
                        zeraNumberResult()
                        contador++
                        return@setOnClickListener
                    }
                    if (contador == 1) {
                        frequencia = numberFloat.roundToInt()
                        binding.numberFim.text = ("Frequencia: $frequencia")
                        contador = 0
                        viewModel.addDataToList(
                            Dados(
                                numero = limiteInferior!!,
                                frequencia = frequencia!!,
                                type = viewModel.typeOfCalc
                            )
                        )
                        zeraTextViews()
                        zeraNumberResult()
                    }
                }
                ActionHome.UNGROUPED_DISCRETE_DATA -> {
                    viewModel.addDataToList(
                        Dados(
                            numero = numberFloat,
                            frequencia = FREQUENCIA_DADOS_NAO_AGRUPADOS,
                            type = viewModel.typeOfCalc
                        )
                    )
                }
            }
        }
    }

    private fun addFrequencia(numberFloat: Float){
        frequencia = numberFloat.roundToInt()
        binding.numberFrequencia.text = ("Frequencia: $frequencia")
        viewModel.addDataToList(createData())
        zeraNumberResult()
        zeraTextViews()
    }

    private fun configuraViewInitEFrequencia(){
        binding.numberInit.visibility = View.VISIBLE
        binding.numberFim.visibility = View.VISIBLE
        binding.numberFrequencia.visibility = View.VISIBLE

        limiteInferior = limiteInferior!! + intervalo!!

        binding.numberInit.text = ("Li: $limiteInferior")
        binding.numberFim.text = ("Intervalo: $intervalo")
        binding.numberFrequencia.text = ("Frequencia:")
    }

    private fun createData(): Dados {
        return Dados(
            classes = Classes(
                limiteInferior = limiteInferior!!,
                limiteSuperior = viewModel.calculaLimiteSuperior(limiteInferior!!, intervalo!!)
            ),
            intervalo = intervalo,
            frequencia = frequencia!!,
            type = viewModel.typeOfCalc,
        )
    }

    private fun zeraNumberResult() {
        binding.numberResult.text = ""
        viewModel.zeraNumero()
    }

    private fun zeraTextViews() {
        if(viewModel.typeOfCalc == ActionHome.DISCRETE_DATA){
            binding.numberInit.text = "Variavel:  "
        }else{
            binding.numberInit.text = "Li: "
        }
        binding.numberFim.text = ""
        binding.numberFrequencia.text = ""
        binding.numberFim.visibility = View.GONE
        binding.numberFrequencia.visibility = View.GONE
        binding.numberInit.visibility = View.VISIBLE
    }
}
*/
