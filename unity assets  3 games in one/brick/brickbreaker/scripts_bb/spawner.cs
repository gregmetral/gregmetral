using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class spawner : MonoBehaviour
{
    [SerializeField] protected GameObject Brick_pf;
    public static int brick_count = 0;
    protected float timer = 3.0f;
    // Start is called before the first frame update
    void Start()
    {
        RandomGeneration();
    }

    // Update is called once per frame
    void Update()
    {
        if (spawner.brick_count == 0)
        {
            timer -= Time.deltaTime;
            if (timer < 0)
            {
                RandomGeneration();
                timer = 1f;
            }
            
        }
    }
    public void RandomGeneration()
    {
        
        float scaleX = Brick_pf.transform.localScale.x;
        float scaleY = Brick_pf.transform.localScale.y;
        float taille = 1.76f * scaleX;
        float startPoint = -(5.8f) + (1.76f)/2;
        float limit = 6;
        

        for (float y = 3; y > -1; y = y -0.5f)
        {
            for (float x = startPoint; x < limit; x = x + taille + 0.03f)
            {
                int spawn = Random.Range(0, 2);
                if (spawn != 0)
                {
                    GameObject newbrick = Instantiate(Brick_pf);
                    newbrick.transform.position = new Vector3(x, y, 0);
                    brick_count++;
                }
                
            }
        }
       
        
    }
}
