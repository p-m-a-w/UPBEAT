package AST;

import Game.*;
import AST.Node.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ASTTest {
    @Test
    public void testIfElse() {
        Game game;
        ExprNode expression;
        ExecNode node, trueNode, falseNode;
        game = new MockupGame();
        trueNode = new DoneNode();
        falseNode = new DoneNode();

        expression = new AtomicNode(0);
        node = new IfElseNode(expression, trueNode, falseNode);
        assertEquals(falseNode, node.execute(game));

        expression = new AtomicNode(1);
        node = new IfElseNode(expression, trueNode, falseNode);
        assertEquals(trueNode, node.execute(game));

        expression = new AtomicNode(2);
        node = new IfElseNode(expression, trueNode, falseNode);
        assertEquals(trueNode, node.execute(game));

        expression = new AtomicNode(-1);
        node = new IfElseNode(expression, trueNode, falseNode);
        assertEquals(falseNode, node.execute(game));

        expression = new AtomicNode(-2);
        node = new IfElseNode(expression, trueNode, falseNode);
        assertEquals(falseNode, node.execute(game));
    }

    @Test
    public void testWhile() {
        Game game;
        ExprNode expression;
        ExecNode node, trueNode, nextNode;
        game = new MockupGame();
        trueNode = new DoneNode();
        nextNode = new DoneNode();

        expression = new AtomicNode(0);
        node = new WhileNode(expression, trueNode);
        node.next = nextNode;
        assertEquals(nextNode, node.execute(game));

        expression = new AtomicNode(1);
        node = new WhileNode(expression, trueNode);
        assertEquals(trueNode, node.execute(game));

        expression = new AtomicNode(2);
        node = new WhileNode(expression, trueNode);
        assertEquals(trueNode, node.execute(game));

        expression = new AtomicNode(-1);
        node = new WhileNode(expression, trueNode);
        assertNull(node.execute(game));

        expression = new AtomicNode(-2);
        node = new WhileNode(expression, trueNode);
        assertNull(node.execute(game));
    }

    @Test
    public void testAssignmentNode() {
        String key;
        Game game = new MockupGame();
        ExecNode node;
        Map<String, Long> map = game.getIdentifiers();

        key = "x";
        node = new AssignmentNode(key, new AtomicNode(-1));
        node.execute(game);
        assertEquals(-1L, map.get(key));

        key = "y";
        node = new AssignmentNode(key, new AtomicNode(0));
        node.execute(game);
        assertEquals(0, map.get(key));

        key = "z";
        node = new AssignmentNode(key, new AtomicNode(1));
        node.execute(game);
        assertEquals(1, map.get(key));
    }

    @Test
    public void testAtomicNode() {
        String key;
        Game game = new MockupGame();
        ExecNode node;
        Map<String, Long> map = game.getIdentifiers();

        key = "x";
        node = new AssignmentNode(key, new AtomicNode(-1));
        node.execute(game);
        assertEquals(-1L, map.get(key));

        key = "y";
        node = new AssignmentNode(key, new AtomicNode(0));
        node.execute(game);
        assertEquals(0, map.get(key));

        key = "z";
        node = new AssignmentNode(key, new AtomicNode(1));
        node.execute(game);
        assertEquals(1, map.get(key));
    }

    @Test
    public void testWhileNode() {
        Game game = new MockupGame();
        ExecNode node;
        node = new WhileNode(new AtomicNode(1), null);
        node.next = new DoneNode();
        while (node != null) {
            node = node.execute(game);
            if (!(node instanceof WhileNode) && node != null)
                assertInstanceOf(DoneNode.class, node);
        }
    }
}
