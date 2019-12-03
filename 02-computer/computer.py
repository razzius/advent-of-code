class ProgramFinished(Exception):
    pass


def do_opcode(instruction, memory):
    # instruction = [1 0 0 0]
    opcode = instruction[0]  # 99

    if opcode == 99:
        raise ProgramFinished

    first_address = instruction[1]
    second_address = instruction[2]

    destination = instruction[3]

    parameter_1 = memory[first_address]
    parameter_2 = memory[second_address]

    if opcode == 1:
        result = parameter_1 + parameter_2
        memory[destination] = result
    elif opcode == 2:
        result = parameter_1 * parameter_2
        memory[destination] = result
    else:
        raise ValueError('Unrecognized opcode {}'.format(opcode))

    return memory


def evaluate(memory, noun, verb):
    instruction_pointer = 0
    length = len(memory)

    memory[1] = noun
    memory[2] = verb

    while instruction_pointer < length:
        start = instruction_pointer
        end = start + 4

        instruction = memory[start:end]

        try:
            new_state = do_opcode(instruction, memory)
        except ProgramFinished:
            return memory

        instruction_pointer += 4

    return new_state


def main():
    text = input()

    input_values = tuple(
        int(value)
        for value in text.split(',')
    )

    want = 19690720

    for noun_guess in range(99):
        for verb_guess in range(99):
            memory = list(input_values)
            result = evaluate(memory, noun_guess, verb_guess)

            first_memory = result[0]
            if first_memory == want:
                print(noun_guess, verb_guess)
                return

    print('No solution found!!')


if __name__ == '__main__':
    main()
